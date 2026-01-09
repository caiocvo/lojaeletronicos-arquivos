# 🛒 Sistema de Loja em Java (Aplicação Console)

![Java](https://img.shields.io/badge/Java-8%2B-blue?logo=java)  
![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow)  
![Licença](https://img.shields.io/badge/Licença-MIT-green)  
![Plataforma](https://img.shields.io/badge/Plataforma-Console-lightgrey)  

Este projeto é uma aplicação de console desenvolvida em **Java**, com foco no aprendizado prático de **Programação Orientada a Objetos (POO)**, organização em camadas, validação de regras de negócio e persistência de dados em arquivos.

O sistema simula o funcionamento básico de uma loja, permitindo o gerenciamento de clientes, produtos, carrinho de compras e pedidos, além de um menu administrativo separado.

---

# 🎯 Objetivo do Projeto
- Aplicar conceitos de POO na prática  
- Trabalhar com leitura e escrita em arquivos  
- Organizar o código em camadas bem definidas  
- Implementar validações e regras de negócio  
- Simular um fluxo real de compra em uma loja  

---

# ✨ Funcionalidades

## 👤 Cliente
- Cadastro de clientes com endereço  
- Validação de nome e e-mail  
- Login de cliente  
- Listagem de clientes (acesso administrativo)  

## 📦 Produtos (Administrador)
- Cadastro de produtos  
- Listagem de produtos  
- Atualização de produtos  
- Exclusão de produtos  
- Validações: nome vazio, preço inválido, estoque negativo  

## 🛒 Carrinho de Compras
- Criação de carrinho para o cliente logado  
- Adição e remoção de produtos  
- Atualização da quantidade de itens  
- Cálculo automático do valor total  

## 📑 Pedidos
- Finalização de pedido a partir do carrinho  
- Geração automática de ID do pedido  
- Associação do pedido ao cliente  
- Armazenamento dos itens do pedido  
- Exibição do valor total da compra  

## 🔐 Administrador
- Login administrativo  
- Menu exclusivo do administrador  
- Gerenciamento de produtos  
- Visualização de clientes cadastrados  
- Mensagens claras em caso de erro de login  

---

# 🏗️ Organização do Projeto
```text
src/
 ├── models/        # Entidades do sistema (Cliente, Produto, Carrinho, Pedido, etc.)
 ├── service/       # Lógica de negócio e fluxo das operações
 ├── exception/     # Tratamento de exceções e classes de validação
 ├── util/          # Manipulação de arquivos e controle de IDs
 └── App.java       # Classe principal responsável pelos menus e interação
```

---

# 💾 Persistência de Dados
- Dados armazenados em arquivos `.txt`  
- Controle de IDs feito por arquivos separados  
- Registros armazenados em linhas com separador `;`  
- Dados persistem mesmo após encerrar o programa  

---

# 🛠️ Tecnologias Utilizadas
- Java (JDK 8 ou superior)  
- Programação Orientada a Objetos  
- Manipulação de arquivos  
- Tratamento de exceções  
- Aplicação console  
- Estrutura modular e escalável  

---

# ▶️ Como Executar
1. Clone o repositório  
2. Abra o projeto em uma IDE Java (IntelliJ, Eclipse ou VS Code)  
3. Execute a classe `App.java`  
4. Utilize os menus no console para interagir com o sistema  

---

# ✅ Regras e Validações
- Nome do cliente não pode ser vazio  
- E-mail deve estar em formato válido  
- Preço do produto deve ser maior que zero  
- Estoque não pode ser negativo  
- Carrinho não pode ser finalizado vazio  
- IDs são gerados automaticamente  
- Fluxo de compra controlado e consistente  

---

# 🚀 Melhorias Futuras
- Validação de estoque antes de adicionar ao carrinho  
- Atualização automática do estoque após a compra  
- Histórico de pedidos por cliente  
- Validação de telefone  
- Integração com banco de dados (MySQL)  
- API REST com Spring Boot  
- Interface gráfica ou aplicação web  

---

# 👨‍💻 Autor
**Desenvolvido por Caio**  
Estudante de Sistemas de Informação  
Foco em Java, backend e arquitetura de software  

---

