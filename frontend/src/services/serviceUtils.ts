export const authHeaders = (token:string) => {
    return {"Authorization" : `Bearer ${token}`}
}
export const jsonHeaders = () => {
    return {"Content-Type": "application/json"}
}
