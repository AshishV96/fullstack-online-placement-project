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
    let token = localStorage.getItem(tokenName)
    if (token != null)
        {
            let decrypted = jwt_decode('.' + decryptToken(token).split('.')[1] + '.')
            return decrypted
        }

    else
        return null
}

export function saveToken(token) {
    localStorage.setItem(tokenName, encryptToken(token))
}

export function getToken() {
    let token = localStorage.getItem(tokenName);
    if (token!= null)
        return decryptToken(token)

    else
        return null
}