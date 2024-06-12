export class Page<T> {
    currentPage: number;
    limit: number;
    totalItems: number;
    totalPages: number;
    data: T[];
}