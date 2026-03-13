<div align="center">

# 🛡️ `<DevTask-Manager/>`

🛡️ Onde a gestão encontra a maestria! Um sistema robusto de gerenciamento de projetos em Java, focado em **Programação Orientada a Objetos avançada**. Demonstra o fluxo real de trabalho: desde a criação de tarefas até sistemas complexos de aprovação e auditoria. **100% Java puro, sem dependências.**

![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge\&logo=openjdk\&logoColor=white)
![OOP](https://img.shields.io/badge/OOP-Advanced-success?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Complete-brightgreen?style=for-the-badge)

### ⚙️ Corporate Logic Meets Clean Code

*Sistema de gestão que aplica Polimorfismo e Persistência de forma robusta*

[⚙️ Como Executar](#-como-executar) • [🧠 Arquitetura](#-conceitos-poo-aplicados) • [🚀 Funcionalidades](#-funcionalidades)

</div>

---

# 🧠 Sobre o Projeto

**`<DevTask-Manager/>`** é mais que um gerenciador de tarefas; é uma **simulação de hierarquia corporativa**.

O sistema utiliza **níveis de acesso** (Gerente, Coordenador, Desenvolvedor) para definir o que cada usuário pode visualizar ou alterar, incluindo um **fluxo de aprovação para mudanças críticas**.

### 🧩 Diferencial

Implementação de **persistência em arquivos planos (`.txt`)**, simulando um banco de dados relacional e mantendo a integridade entre **Usuários, Projetos e Tarefas**.

---

# 🚀 Funcionalidades

## 👥 Gestão de Acessos (RBAC)

* **Gerente:** Controle total, criação de projetos e aprovação de prioridades
* **Coordenador:** Gestão de tarefas e aprovação de mudanças de status
* **Desenvolvedor:** Foco na execução e solicitação de alterações

---

## 🛡️ Fluxo de Aprovação

* Mudanças sensíveis (Prioridade/Status) geram uma `SolicitacaoAlteracao`
* Sistema de comentários integrado para auditoria
* Histórico de ações persistido para transparência

---

## 💾 Persistência de Dados

* Leitura e escrita em arquivos **CSV customizados**
* Sistema de **Auto-save** ao realizar operações críticas
* Carregamento dinâmico de dados ao iniciar a aplicação

---

# 🧱 Estrutura do Projeto

```text
devtask-manager/
├── src/
│   ├── application/       # Ponto de entrada (Main)
│   │
│   ├── model/
│   │   ├── entities/      # Modelos (Usuario, Projeto, Tarefa...)
│   │   ├── services/      # Lógica de negócio e persistência
│   │   ├── enums/         # Status e prioridades
│   │   ├── exceptions/    # Tratamento de erros customizados
│   │   └── contexto/      # Gerenciamento de estado da aplicação
│   │
│   └── menu/              # Interfaces CLI por nível de acesso
│
└── dados/                 # "Banco de dados" em .txt
```

---

# 🧠 Conceitos POO Aplicados

## ⚙️ Polimorfismo e Herança

As permissões não são tratadas com `if/else` gigantes, mas sim com comportamentos definidos nas subclasses de `Usuario`.

```java
public abstract class Usuario {
    public abstract boolean podeCriarProjeto();
}
```

---

## 🧯 Tratamento de Exceções Customizado

Hierarquia de erros para garantir que falhas de negócio não quebrem a aplicação.

```java
if (projeto == null) {
    throw new ProjetoException("Projeto não encontrado");
}
```

---

## 🔒 Encapsulamento e Serialização Manual

Uso rigoroso de modificadores de acesso e métodos `toFileString()` para persistência manual.

---

# 🖥️ Exemplo de Uso

```
===== MENU LOGIN =====
1 - Login
2 - Criar Usuário
0 - Sair

👤 Email: gerente@empresa.com
🔑 Senha: ******

===== MENU GERENTE =====
1 - Criar Projeto
2 - Criar Tarefa
3 - Aprovar Solicitações (2 pendentes)
0 - Sair

> Criando tarefa "Refatorar Persistência" para o desenvolvedor Ana...
✅ Tarefa salva com sucesso!
```

---

# ⚙️ Como Executar

## 📦 Pré-requisitos

* Java **JDK 11+** (Recomendado **Java 17**)

---

## 🚀 Passos

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/devtask-manager.git

# Entre na pasta
cd devtask-manager

# Compile
javac -d bin src/**/*.java

# Execute
java -cp bin application.Main
```

---

# 📌 Diferenciais Técnicos

* ⚙️ **Zero Frameworks** – toda a lógica foi implementada manualmente
* 🔍 **Validação de dados** com Regex
* 🧩 **Arquitetura modular**, preparada para GUI ou banco de dados real

---

# 👩‍💻 Sobre

Sou **Ana Luiza**, desenvolvedora focada em **Backend**.

Este projeto demonstra meu domínio em **arquitetura Java, modelagem orientada a objetos e manipulação de dados**, simulando um ambiente corporativo real.

### 🎯 Minha visão

> "Código limpo não é apenas estética, é sobrevivência em sistemas complexos."

---

# 📫 Contato

📧 Email
[a.luiza.fullstack@gmail.com](mailto:a.luiza.fullstack@gmail.com)

💼 LinkedIn
https://linkedin.com/in/analuizafullstack

🐙 GitHub
https://github.com/aluizafullstack

---

<div align="center">

Desenvolvido com ☕ Java e 🛡️ foco em arquitetura

</div>

