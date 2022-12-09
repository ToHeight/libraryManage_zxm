import {JSEncrypt} from 'jsencrypt'
//公钥
let PUBLIC_KEY = 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCADB+zg4Ou3fv6rY8159gw4fkJbuMPeM41ttw20leKjSKQWOgBixHJjXbkRvoMmUQkWq67xWzpMgKB7t8LIJx+n0dLP+6YDqbfFEJJ2i1Va4U1yJyGht0bEW0tpadKX3i5JwUwQIBPiC7VSWhtVyAKtzTYeun/fqpxTDAbulrj4QIDAQAB';
//私钥
let PRIVATE_KEY = 'MIICWwIBAAKBgQCADB+zg4Ou3fv6rY8159gw4fkJbuMPeM41ttw20leKjSKQWOgBixHJjXbkRvoMmUQkWq67xWzpMgKB7t8LIJx+n0dLP+6YDqbfFEJJ2i1Va4U1yJyGht0bEW0tpadKX3i5JwUwQIBPiC7VSWhtVyAKtzTYeun/fqpxTDAbulrj4QIDAQABAoGACj/Y2m0orBAfvHvfrpBtc9LlX2sX/g6M7wFr6hrMdWOBBJiL5Z0PTO39D3Ow +IjcyqN+62UiUnOK04IJKiJaSa1HNWagW2aAOblca1lYyYD6wlUotMV3bgk9lly0dD0lUTd8XWOmo1NdTEFW7y1OB4pYgMcT+iv4o0cr4sAtWisCQQCD6EmjEpMI5dcfZcrSXbT+WQGvdVCjAhivVMbNYeZq37ARt+9mTnaoA6Ss/QGQ5qvO9jMhx8x9/e8EfA+AX2rzAkEA+II3IXRXY3xbjDnK84kunlWpImH6XofN2V/TGEH1/Iqa909PHhuL4mhSt0iC70/y1g5kbmXyXE5s5gEsPqmC2wJAAU9uY9NMaJs33tT5Bcvuf1RNAvwsV+Iucpdp/iJJ0qf0LMjh9Oc0oIiguyMsP886x6yEZ4J/koTSOf4tfT31ZwJAMs28I5S7QNVtic9O1FbZNvlgKG1LWAP/a08RwsXJWiWj5KdMD2WmRVT6hAnI6s+3X1d15LPmxkQqMyNOPkk9PQJAJyPGWOjrCjzwojE0lN4NtS9brx6JbPy/sFkHX5LN8Xv45+XOKp14JgRcABTfWfvnnoWoWKha2cyJFlf8AdCIuQ==';
//使用公钥加密
let encrypt = new JSEncrypt();//使用私钥解密
export function publicKsy(data) {
    encrypt.setPublicKey('-----BEGIN PUBLIC KEY-----' + PUBLIC_KEY + '-----END PUBLIC KEY-----');
    let result = JSON.stringify(data);
    return encrypt.encrypt(result);
}

let decrypt = new JSEncrypt();
export function privateKey(data) {
    decrypt.setPrivateKey('-----BEGIN RSA PRIVATE KEY-----'+PRIVATE_KEY+'-----END RSA PRIVATE KEY-----');
    return decrypt.decrypt(data);
}
