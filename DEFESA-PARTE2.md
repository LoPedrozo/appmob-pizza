# Fatia — Defesa da Parte 2

Navegação entre telas com rotas + login e cadastro simples.

## 1. O que mudou da Parte 1 para a Parte 2

Na Parte 1 a troca de telas era feita **na mão**, dentro da `MainActivity`:

```kotlin
var telaAtual by remember { mutableStateOf("login") }
when (telaAtual) {
    "login" -> LoginScreen(onEntrar = { telaAtual = "cardapio" })
    ...
}
```

O nome da tela era um texto solto, e para passar um dado de uma tela para
outra a gente precisava guardar numa variável extra (`pizzaEscolhida`).

Na Parte 2 isso foi substituído por **NavHost + rotas**, no pacote
`com.example.fatia.navigation`:

- `Routes.kt` — cada tela virou um tipo (`Login`, `Cardapio`, `Personalizar`…)
- `AppNavigation.kt` — o `NavHost` com um `composable<Rota> { }` por tela
- `Menu.kt` — a `TopAppBar` com os atalhos

A `MainActivity` ficou só com `rememberNavController()` + `AppNavigation(...)`.
O composable `AppFatia` e a variável `telaAtual` foram removidos.

Ganhos: o argumento agora viaja **com tipo** dentro da rota
(`Personalizar(val nomePizza: String)`), e o Android passa a cuidar da pilha
de telas — o botão "voltar" do celular funciona sozinho.

## 2. Mapa das rotas

| De | Ação | Para |
|---|---|---|
| Login | login válido | `Cardapio` |
| Login | "Criar conta" | `Cadastro` |
| Cadastro | cadastro OK / "Já tenho conta" | `popBackStack()` (volta ao Login) |
| Cardapio | clicar numa pizza | `Personalizar(nomePizza)` |
| Personalizar | botão "Adicionar" | `Confirmacao(nomePizza)` |
| Confirmacao | "Voltar ao início" | `popBackStack()` |
| Menu | Cardápio / Personalizar / Sair | rota correspondente |

**Telas sem menu:** Login e Cadastro. **Telas com menu:** Cardápio,
Personalizar e Confirmação.

## 3. Perguntas prováveis do professor

**Por que as rotas são `@Serializable`?**
Porque o NavHost precisa guardar a rota na pilha de navegação e reconstruí-la
depois. A biblioteca `kotlinx-serialization` é quem sabe transformar o objeto
da rota em dados e depois montar o objeto de volta. É por causa disso que dá
para ter argumento **com tipo** (`String`, `Int`) em vez de tudo virar texto
na URL da rota.

**Qual a diferença entre `data object` e `data class` numa rota?**
`data object` é rota **sem argumento** — existe uma cópia só dela
(`Login`, `Cadastro`, `Cardapio`). `data class` é rota **com argumento**: cada
navegação cria um objeto novo carregando o valor
(`Personalizar("Calabresa")`), igual a um parâmetro de função.

**O que `toRoute<Confirmacao>()` faz e de onde vem o argumento?**
Dentro do `composable<Confirmacao> { back -> ... }`, o `back` é a entrada da
pilha de navegação (`NavBackStackEntry`). O `toRoute<Confirmacao>()` lê os
dados guardados nessa entrada e remonta o objeto `Confirmacao`, já com o
`nomePizza` dentro. O valor veio de quem navegou: a tela Personalizar chamou
`navController.navigate(Confirmacao(nome))`.

**Qual a diferença entre `navigate()` e `popBackStack()`? O que é a pilha?**
A pilha de navegação é uma pilha de telas, como uma pilha de pratos: a que
está no topo é a que aparece. `navigate(Rota)` **empilha** uma tela nova em
cima. `popBackStack()` **tira** a tela do topo, então volta para a anterior —
é o mesmo efeito do botão "voltar" do celular.

**Onde os usuários ficam guardados? Por que a lista sobrevive à troca de tela?**
Em `dados/RepositorioUsuarios.kt`, numa `mutableListOf` dentro de um
`companion object`. O `companion object` é o equivalente ao `static` do Java:
existe **uma cópia só** da lista, ligada à classe e não a cada objeto criado.
Como ela não está dentro de um composable, não é recriada quando a tela é
redesenhada nem quando trocamos de rota.

**E por que ela some ao fechar o app?**
Porque é só memória, não é banco de dados. Quando o app é encerrado, o
processo morre e a lista vai junto. Guardar de verdade exigiria banco
(Room) ou arquivo, que não foi dado em aula.

**Por que o menu não aparece no Login e no Cadastro?**
Porque o `Scaffold` com a `TopAppBar` **não** envolve o `NavHost` inteiro.
Ele fica dentro de cada `composable<Rota>` que deve ter menu. As rotas
`Login` e `Cadastro` chamam a tela direto, sem `Scaffold` — então não existe
barra nenhuma para desenhar.

**Por que `remember` / `mutableStateOf` é necessário nos campos?**
O Compose redesenha a tela (recomposição) toda vez que algo muda. Uma
variável comum seria recriada e zerada nesse redesenho, e a letra digitada
sumiria. `mutableStateOf` cria um valor **observável**: quando ele muda, o
Compose redesenha o que depende dele. O `remember` faz esse valor sobreviver
às recomposições seguintes.

## 4. Como testar na apresentação

1. **Login com o usuário de exemplo** — e-mail `teste@fatia.com`, senha
   `123456` → entra no Cardápio (agora com o menu no topo).
2. **Senha errada** — digite qualquer senha errada → aparece
   "E-mail ou senha inválidos" em vermelho, abaixo dos campos.
3. **Criar conta nova** — toque em "Criar conta", preencha os quatro campos
   (senha com 4 ou mais caracteres, e igual à confirmação — antes disso o
   botão fica apagado), toque em "Cadastrar" → volta ao Login. Entre com o
   e-mail e a senha que você acabou de criar.
4. **E-mail repetido** — tente cadastrar de novo o mesmo e-mail → aparece
   "Este e-mail já está cadastrado".
5. **Fluxo completo** — Cardápio → toque numa pizza → Personalizar (o nome da
   pizza veio pela rota) → "Adicionar" → Confirmação com o mesmo nome →
   "Voltar ao início".
6. **Menu** — nas três telas internas, use Cardápio / Personalizar / Sair.
   Confirme que no Login e no Cadastro **não existe** barra no topo.
