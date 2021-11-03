

const parseJwt = (token: string) => {
    try {
        return JSON.parse(atob(token.split(".")[1]));
    } catch (e) {
        return null;
    }
}

export const validateToken = (token: string): boolean => {
    const decodedJwt = parseJwt(token);
    if (!decodedJwt) return false;
    return decodedJwt.exp * 1000 > Date.now()
}
