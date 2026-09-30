# 🎙️ Dio Budgeting - Assistente Financeiro Inteligente com Spring AI & Voice AI

Projeto prático desenvolvido durante a trilha de **Spring Boot & IA** da [Digital Innovation One (DIO)](https://www.dio.me/), sob orientação do expert **Thiago Poiani** (Senior Java Developer).

A aplicação implementa um assistente financeiro multimodal capaz de processar linguagem natural e comandos de voz, convertendo fala em dados estruturados de transações financeiras, persistindo registros por meio de **Tool Calling** e devolvendo respostas em áudio sintetizado (**Text-to-Speech**).

---

## 🚀 Fluxo da Aplicação

```text
[ Usuário / Áudio ] 
       │
       ▼
[ POST /transactions/ai ]
       │
       ├─► 1. Storage & Auditoria: Gravação física do arquivo de áudio recebido
       │
       ├─► 2. Transcrição (STT): OpenAI Whisper-1
       │
       ├─► 3. Orquestração: Spring AI ChatClient + System Message
       │        │
       │        └─► 4. Tool Calling: Identifica intenção e executa Use Cases:
       │                 • PersistTransactionUseCase (Descrição, Valor em centavos, Categoria)
       │                 • ListTransactionsByCategoryUseCase (Consultas no banco)
       │
       ├─► 5. Persistência: Spring Data JPA ➔ MySQL 9
       │
       └─► 6. Síntese de Voz (TTS): OpenAI TTS ➔ Retorno do áudio MP3
```

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 21 (LTS)
- **Framework:** Spring Boot 3.3.4
- **IA Generativa:** Spring AI 2.0.0-M4 (`spring-ai-starter-model-openai`)
- **Persistência:** Spring Data JPA, Hibernate, MySQL 9
- **Resiliência & Integração:** Spring Web Client (`RestClient`)
- **Conteinerização:** Docker & Docker Compose
- **Build Tool:** Gradle
- **Testes & Asserções:** JUnit 5, AssertJ, Spring Boot Test

---

## 🏗️ Arquitetura do Projeto

O projeto segue princípios de **Clean Architecture** e isolamento de domínio:

```text
src/main/java/dio/budgeting/
├── application/                     # Casos de uso e DTOs de entrada/saída
│   ├── input/PersistTransactionInput.java
│   ├── output/TransactionOutput.java
│   ├── ListTransactionsByCategoryUseCase.java
│   └── PersistTransactionUseCase.java
├── domain/                          # Entidades e contratos puros de negócio
│   ├── Category.java
│   ├── Transaction.java
│   ├── TransactionId.java
│   └── TransactionRepository.java
└── infrastructure/                  # Adaptadores externos, Web, Persistência e MCP
    ├── client/                      # Verificações e chamadas HTTP externas (FraudCheck)
    ├── http/                        # Controllers REST e mapeadores
    │   ├── request/TransactionRequest.java
    │   ├── response/TransactionResponse.java
    │   └── TransactionController.java
    ├── mcp/                         # Servidor agnóstico de ferramentas (Model Context Protocol)
    │   └── McpServerController.java
    ├── persistence/                 # Implementação de banco de dados
    │   ├── entity/TransactionEntity.java
    │   └── repository/JpaTransactionRepository.java
    └── storage/                     # Gravação física de áudios brutos para auditoria
        └── AudioStorageService.java
```

---

## ⚙️ Como Executar o Projeto

### Pré-requisitos
- JDK 21 instalado
- Docker e Docker Compose em execução
- Chave de API da OpenAI (`OPENAI_API_KEY`)

### 1. Clonar o repositório
```bash
git clone [https://github.com/diegofloriano/dio-budgeting-spring-ai.git](https://github.com/diegofloriano/dio-budgeting-spring-ai.git)
cd dio-budgeting-spring-ai
```

### 2. Configurar a Chave da OpenAI
No Windows (PowerShell):
```powershell
$env:OPENAI_API_KEY="sua-chave-openai-aqui"
```
No Linux / macOS:
```bash
export OPENAI_API_KEY="sua-chave-openai-aqui"
```

### 3. Iniciar o Banco de Dados com Docker
Suba a instância do MySQL configurada no projeto:
```bash
docker compose up -d
```

### 4. Executar a Aplicação
```bash
./gradlew bootRun
```
A API estará disponível em `http://localhost:8080`.

---

## 📍 Endpoints da API

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/transactions/ai` | Recebe áudio (`multipart/form-data`), armazena para auditoria, transcreve com Whisper, executa Tool Calling e responde em MP3 |
| `POST` | `/transactions` | Criação manual de transação financeira |
| `GET` | `/transactions/{category}` | Lista despesas por categoria (`GROCERIES`, `PHARMA`, `AUTO`) |
| `GET` | `/api/chat-model?prompt=...` | Interação direta com o modelo GPT via prompt |
| `POST` | `/api/transcribe` | Transcrição isolada de arquivo de áudio |
| `POST` | `/api/sinthesize` | Síntese avulsa de texto para áudio MP3 |
| `GET` | `/api/mcp/tools` | Lista as ferramentas disponíveis no padrão Model Context Protocol |
| `POST` | `/api/mcp/call` | Execução agnóstica de ferramentas para backends externos (Node.js, Python, PHP) |

---

## 🌟 Funcionalidades Avançadas e Diferenciais

- **Auditoria Forense & Storage:** Cada áudio enviado é armazenado no sistema de arquivos local (`dio_budgeting_audio`) e indexado ao identificador do usuário e data de execução.
- **Protocolo Aberto (MCP):** Arquitetura preparada para integração agnóstica de ferramentas com outros serviços legados via MCP Server.
- **Tratamento de Moeda em Centavos:** Conversão de valores decimais em inteiros (`long`) para evitar erros de ponto flutuante em finanças.
- **Testes Condicionais:** Bateria de testes configurada para execução sem quebras em ambientes de CI/CD que não possuam chaves de terceiros configuradas.

---

## 👨‍💻 Autor

Desenvolvido por **Diego Floriano Costa**  
Trilha Spring Boot & Spring AI — **Digital Innovation One (DIO)**.
