import { TestBed, inject, async, ComponentFixture } from '@angular/core/testing';

import { ApiService } from './api.service';
import { HttpClient, HttpClientModule } from '@angular/common/http';

describe('ApiService', () => {

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            imports: [
                HttpClientModule
            ]
        })
            .compileComponents();
    }));

    it('should be created', () => {
        inject([ApiService], (apiService: ApiService) => {
            expect(apiService).toBeDefined();
        });
    });

    it('should return movies', async () => {
        inject([ApiService], async (apiService: ApiService) => {
            let data = await apiService.getMovies();
            expect(data).toBeDefined();
        });
    });

    it('should return characters', async () => {
        inject([ApiService], async (apiService: ApiService) => {
            let data = await apiService.getCharacters();
            expect(data).toBeDefined();
        });
    });
});
