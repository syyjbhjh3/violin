export interface ApiResponse<T> {
    resultMessage: string;
    result: string;
    data: T;
}