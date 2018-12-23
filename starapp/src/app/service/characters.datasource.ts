import { DataSource } from '@angular/cdk/table';
import { BehaviorSubject, Observable, of, from, forkJoin } from 'rxjs';
import { catchError, map, tap, finalize, mergeMap, take, concatAll, reduce } from 'rxjs/operators';
import { CollectionViewer } from '@angular/cdk/collections';
import { PeopleList } from '../model/people-list';
import { ApiService } from './api.service';
import { Person } from '../model/person';
import { MatPaginator, PageEvent } from '@angular/material';
import { HttpClient } from '@angular/common/http';

export class PersonDataSource implements DataSource<Person> {

    private dataSubject = new BehaviorSubject<Person[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);
    private querySubject = new BehaviorSubject<string>('');
    public loading$ = this.loadingSubject.asObservable();

    private planetsCache = {};
    private speciesCache = {};

    get searchQuery(): string {
        return this.querySubject.value;
    }

    set searchQuery(value: string) {
        this.querySubject.next(value);
    }

    constructor(private http: HttpClient, private apiService: ApiService, private paginator: MatPaginator) {
        this.configurePaginator();
        this.configureSearch();
    }

    connect(collectionViewer: CollectionViewer): Observable<Person[]> {
        return this.dataSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.dataSubject.complete();
        this.loadingSubject.complete();
        this.querySubject.complete();
    }

    loadCharacters(search: string = '', page: number = 1) {

        this.loadingSubject.next(true);

        this.apiService
            .getCharacters(search.trim().toLowerCase(), page)
            .pipe(
                catchError(() => of([])),
                finalize(() => this.loadingSubject.next(false))
            )
            .subscribe((data: PeopleList) => {
                this.updatePaginator(data);

                from(data.results)
                    .pipe(
                        mergeMap((character: any) => {
                            let requests = [];

                            let planetUrl = character.homeworld;
                            let speciesUrl = character.species ? character.species[0] : null;

                            //homeworld
                            if (planetUrl) {
                                if (this.planetsCache[planetUrl]) {
                                    requests.push(of(this.planetsCache[planetUrl]));
                                }
                                else {
                                    requests.push(this.http.get(planetUrl));
                                }
                            }

                            //species
                            if (speciesUrl) {
                                if (this.speciesCache[speciesUrl]) {
                                    requests.push(of(this.speciesCache[speciesUrl]));
                                }
                                else {
                                    requests.push(this.http.get(speciesUrl));
                                }
                            }

                            return forkJoin(requests)
                                .pipe(
                                    map((reqs: any) => {
                                        
                                        //update cache
                                        this.planetsCache[planetUrl] = reqs[0];
                                        this.speciesCache[speciesUrl] = reqs[1];

                                        //set values
                                        character.homeworld = reqs[0];
                                        character.species = reqs[1];
                                    })
                                )
                        })
                    )
                    .subscribe((people: any) => {

                    });

                this.dataSubject.next(data.results)
            });
    }

    updatePaginator(peopleList: PeopleList) {
        if (this.paginator) {
            this.paginator.length = peopleList.count;
        }
    }

    configurePaginator() {
        if (this.paginator) {
            this.paginator.hidePageSize = true;
            this.paginator.pageSize = 10;
            this.paginator.page.subscribe((pageEvent: PageEvent) => {
                this.loadCharacters(this.querySubject.value, pageEvent.pageIndex + 1);
            });
        }
    }

    configureSearch() {
        this.querySubject.subscribe((query: String) => {
            this.loadCharacters(this.querySubject.value, 1);
        });
    }
}

