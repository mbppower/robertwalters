import { Person } from './person';

export interface PeopleList {
    count: number;
    next: string;
    previous: string;
    results: Person[];
}
