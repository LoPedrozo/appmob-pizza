package com.example.fatia.dados

// ONDE OS USUARIOS FICAM GUARDADOS
//
// A lista fica dentro de um companion object. Isso quer dizer que existe
// UMA copia so dela, ligada a classe e nao a cada objeto criado - e o
// mesmo papel do static do Java, igual a classe Contador da aula.
//
// E por isso que um usuario cadastrado continua existindo quando trocamos
// de tela: a lista nao e recriada junto com a tela.
//
// Atencao: isto NAO e banco de dados. A lista vive na memoria, entao ao
// fechar o app tudo o que foi cadastrado se perde.
class RepositorioUsuarios {

    companion object {

        // mutableListOf = lista que aceita adicionar e remover itens.
        // Ja comeca com um usuario de exemplo, para conseguirmos
        // demonstrar o login sem precisar cadastrar antes.
        private val usuarios = mutableListOf(
            Usuario("Teste", "teste@fatia.com", "123456")
        )

        // Procura um usuario pelo e-mail.
        // find percorre a lista e devolve o primeiro que combina,
        // ou null se nao achar ninguem.
        fun buscarPorEmail(email: String): Usuario? {
            return usuarios.find { it.email == email }
        }

        // Tenta cadastrar um usuario novo.
        // Devolve false se o e-mail ja estiver em uso, true se cadastrou.
        fun cadastrar(usuario: Usuario): Boolean {
            if (buscarPorEmail(usuario.email) != null) {
                return false
            }
            usuarios.add(usuario)
            return true
        }

        // Confere se existe um usuario com aquele e-mail e aquela senha.
        fun autenticar(email: String, senha: String): Boolean {
            val usuario = buscarPorEmail(email)
            // Nao achou ninguem com esse e-mail: nao entra.
            if (usuario == null) {
                return false
            }
            // Achou: so entra se a senha digitada for a mesma do cadastro.
            return usuario.senha == senha
        }

        // Devolve a lista de usuarios cadastrados (somente para leitura).
        fun listar(): List<Usuario> = usuarios
    }
}
