# PokeApi Challenge (EN)

PokeApi showcase app made in Kotlin.

## Features

* Pokémon listed by generation;
* Pagination with infinite scroll;
* Dependency Injection; 
* Offline first; 
* All Pokémon generations available; 
* Add/Remove/View Favorite Pokémon; 
* Pokémon details;
* MotionLayout animations;
* Search for Pokémon.

## Architecture patterns

The app was developed using MVVM as it's presentation pattern along with Repository and a webservice layer above it, followed by a data access layer (Database and API). There is also a Framework layer responsible for looking up localized messages, managing navigation and showing alert messages. The Dependency Inversion is also used to make sure the code remains highly testable. It was also made using Offline First concept, using a local database as a single source of truth.

## Technologies

To manage and inject dependencies, the framework "Koin" was used. In data access, Retrofit is used to fetch network requests and Room is used as the single source of truth, being responsible to displaying all the data to the user through LiveData, together with Data Binding. To manage async requests, Kotlin Coroutines were used. To display the Pokémon generations, the Paging3 Jetpack component was used, as it works out-of-the-box with Room. To display the favorite list, Epoxy was used, as this list didn't require pagination. To draw the Pokémon details appear animation, the MotionLayout component was used. Lottie used to show Pikachu loading.

## Database Structure

The Pokémon Entity contains a many-to-many relations with Ability, Type and Move, and to optimize cache space, the choice was to structure the database relations rather than replicating the data inside Pokémon table.

## Data Mock

The data mock was achieved using Flavors and Dependency injection to separate unnecessary source code from the production apk.

## Unit Tests

The unit tests were written to test the Webservice layer (between Repository and data acess layer) business logic, as it is the only layer that has external dependencies. 

# Desafio PokeApi (PT)

Aplicativo de mostruário de Pokémon desenvolvido em Kotlin.

## Funcionalidades

* Pokémon listados por geração;
* Paginação com scroll infinito; 
* injeção de dependências; 
* Offline First;
* Todas as gerações disponíveis;
* Adicionar/Remover/Listar Pokémon favoritos;
* Detalhes do Pokémon;
* Animações com MotionLayout;
* Busca de Pokémon;

## Padrões arquiteturais

O aplicativo foi desenvolvido usando MVVM como seu Presentation Pattern junto com o Repository Pattern e uma camada de webservice acima dele, seguida de uma camada de acesso a dados acima (banco de dados e API). Há também uma camada de Framework responsável por pesquisar mensagens internacionalizadas, gerenciar a navegação e mostrar mensagens de alerta. O princípio da Inversão de Dependência também é usada para garantir que o código permaneça altamente testável. O app também foi desenvolvido usando o conceito de Offline First, usando um banco de dados local como uma "single source of truth".

## Tecnologias

Para gerenciar e injetar dependências, o framework "Koin" foi usado. No acesso aos dados, o Retrofit é utilizado para fazer as solicitações de API e o Room é utilizado como single source of truth, sendo responsável por exibir todos os dados ao usuário por meio do LiveData, juntamente com o Data Binding. Para realizar solicitações assíncronas, foram usadas as Kotlin Coroutines. Para mostrar as gerações de Pokémon, o componente Paging3 do Jetpack foi usado, pois funciona de imediato com Room. Para exibir a lista de favoritos, foi usado Epoxy, pois esta lista não requer paginação. Para fazer a animação da tela de detalhes do Pokémon, o componente MotionLayout foi usado. Lottie foi utilizado para exibir o carregamento do Pikachu.

## Estrutura do banco de dados

A Entidade Pokémon contém relações muitos-para-muitos com Habilidade, Tipo e Movimento e, para otimizar o espaço do cache, a escolha foi estruturar as relações no banco de dados em vez de replicar os dados dentro da tabela Pokémon.

## Mock de dados

O mock de dados foi feito usando Flavors e injeção de dependências para separar o código-fonte desnecessário do apk de produção.

## Testes unitários

Os testes de unidade foram escritos para testar a regra de negócios da camada de Webservice, pois é a única que possui dependências externas.
