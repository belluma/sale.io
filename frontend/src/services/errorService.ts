export const parseError = (err: any) => {
    return {data: "", status: err.response.status, statusText: err.response.data.message}
}
