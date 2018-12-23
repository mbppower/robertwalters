import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatTableDataSource, MatPaginator } from '@angular/material';
import { ApiService } from '../service/api.service';
import { PersonDataSource } from '../service/characters.datasource';
import { fromEvent, Observable, of } from 'rxjs';
import { map, filter, debounceTime, switchAll, distinctUntilChanged } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';

@Component({
    selector: 'app-characters',
    templateUrl: './characters.component.html',
    styleUrls: ['./characters.component.scss']
})
export class CharactersComponent implements OnInit {
    
    displayedColumns: string[] = ['name', 'height', 'gender', 'birth_year', 'homeworld', 'species'];
    dataSource: PersonDataSource;
    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild('searchInput')
    searchInput: ElementRef;

    constructor(private apiService: ApiService, private http: HttpClient) {

    }

    ngOnInit() {
        this.dataSource = new PersonDataSource(this.http, this.apiService, this.paginator);
        this.dataSource.loadCharacters();

        //delay search queries
        let search$ = fromEvent(this.searchInput.nativeElement, 'keyup')
            .pipe(
                map((e: any) => e.target.value),
                filter((query: string) => !query || query.length >= 2),
                debounceTime(200),
                map((query: string) => of(query)),
                distinctUntilChanged(),
                switchAll()
            )

        //filter results
        search$.subscribe((query) => this.dataSource.searchQuery = query);
    }
}
