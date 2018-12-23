import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApiService } from '../service/api.service';
import { BehaviorSubject, of } from 'rxjs';
import { catchError, finalize } from 'rxjs/operators';

@Component({
    selector: 'app-movies',
    templateUrl: './movies.component.html',
    styleUrls: ['./movies.component.scss']
})
export class MoviesComponent implements OnInit {
    private movies: any[] = [];
    private loadingSubject = new BehaviorSubject<boolean>(false);
    public loading$ = this.loadingSubject.asObservable();

    constructor(private apiService: ApiService) { }

    ngOnInit() {
        this.loadMovies();
    }

    loadMovies(search: string = '', page: number = 1) {

        this.loadingSubject.next(true);

        this.apiService
            .getMovies(search.trim().toLowerCase(), page)
            .pipe(
                catchError(() => of([])),
                finalize(() => this.loadingSubject.next(false))
            )
            .subscribe((data: any) => {
                this.movies = data.results;
            });
    }
}
