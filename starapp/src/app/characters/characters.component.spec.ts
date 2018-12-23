import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CharactersComponent } from './characters.component';
import { RouterTestingModule } from '@angular/router/testing';
import { MaterialModule } from '../material.module';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { ApiService } from '../service/api.service';
import { HttpClient, HttpClientModule } from '@angular/common/http';

describe('CharactersComponent', () => {
    let component: CharactersComponent;
    let fixture: ComponentFixture<CharactersComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            imports: [
                RouterTestingModule,
                MaterialModule,
                NoopAnimationsModule,
                HttpClientModule
            ],
            providers: [
                ApiService,
                HttpClient
            ],
            declarations: [CharactersComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(CharactersComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should have datatable-container class', () => {
        const fixture = TestBed.createComponent(CharactersComponent);
        fixture.detectChanges();
        const compiled = fixture.debugElement.nativeElement;
        expect(compiled.querySelector('.datatable-container')).toBeTruthy();
    });
});
