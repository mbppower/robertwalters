import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class ApiService {

    constructor(private http: HttpClient) { }

    getCharacters(search: string = '', page = 1) {
        let params = new HttpParams().set('page', page.toString());

        if (search) {
            params = params.append('search', search)
        }

        return this.http.get(`${environment.apiUrl}/people/`, {
            params: params
        });
    }

    getCharacterById(id: number) {
        return this.http.get(`${environment.apiUrl}/people/${id}/`);
    }

    getSpeciesById(id: number) {
        return this.http.get(`${environment.apiUrl}/species/${id}/`);
    }

    getPlanetById(id: number) {
        return this.http.get(`${environment.apiUrl}/planets/${id}/`);
    }

    getMovies(search: string = '', page: number = 1) {
        let params = new HttpParams().set('page', page.toString());

        if (search) {
            params.set(search, search)
        }

        return this.http.get(`${environment.apiUrl}/films/`, {
            params: params
        });
    }

    getMovieById(id: number) {
        return this.http.get(`${environment.apiUrl}/films/${id}/`);
    }
}
