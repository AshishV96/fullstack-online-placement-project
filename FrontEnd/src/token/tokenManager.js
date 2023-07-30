import jwt_decode from 'jwt-decode'

export const tokenName = 'token'

export function encryptToken(token) {
    let encrypted = token.replaceAll('b', '!').replaceAll('i', '@').replaceAll('j', '#')
        .replaceAll('m', '%').replaceAll('n', '^').replaceAll('s', '&').replaceAll('w', '*')

    return encrypted
}

export function decryptToken(token) {
    let decrypted = token.replaceAll('!', 'b').replaceAll('@', 'i').replaceAll('#', 'j')
        .replaceAll('%', 'm').replaceAll('^', 'n').replaceAll('&', 's').replaceAll('*', 'w')

    return decrypted
}

export function getBody() {
    if ((token = localStorage.getItem(tokenName)) != null)
        return jwt_decode('.' + decryptToken(token).split('.')[1] + '.')

    else
        return null
}

export function setToken(token) {
    localStorage.setItem(tokenName, encryptToken(token))
}

export function getToken() {
    if ((token = localStorage.getItem(tokenName)) != null)
        return decryptToken(token)

    else
        return null
}