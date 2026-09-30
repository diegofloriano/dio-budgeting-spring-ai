# 🎙️ Dio Budgeting - API Inteligente com Reconhecimento de Fala & Spring AI

Projeto desenvolvido no bootcamp da **Digital Innovation One (DIO)** na trilha de Spring Boot e Inteligência Artificial, orientado pelo expert **Thiago Poiani** (Senior Java Developer).

A aplicação é uma API financeira multimodal que interpreta intenções a partir de áudios de despesas, orquestra **Tool Calling** no modelo de linguagem para extrair e persistir transações em banco relacional, e devolve a confirmação em voz sintetizada.

---

## 🚀 Fluxo da Aplicação

```text
[ Usuário / Áudio ] 
       │
       ▼
[ POST /transactions/ai ]
       │
       ├─► 1. Transcrição: OpenAI Whisper-1 (Speech-to-Text)
       │
       ├─► 2. Orquestração: Spring AI ChatClient + Prompt de Sistema
       │        │
       │        └─► 3. Tool Calling: Identifica intenção e executa Use Cases:
       │                 • PersistTransactionUseCase (Gasto, Valor, Categoria)
       │                 • ListTransactionsByCategoryUseCase (Consultas)
       │
       ├─► 4. Persistência: Spring Data JPA ➔ MySQL 9
       │
       └─► 5. Síntese de Voz: OpenAI TTS (Text-to-Speech) ➔ Áudio MP3 de resposta
```

---

## 🏗️ Arquitetura e Estrutura do Código

O projeto adota uma arquitetura em camadas orientada ao domínio:

```text
src/main/java/dio/budgeting/
├── application/                     # Casos de uso e DTOs de entrada/saída
│   ├── input/PersistTransactionInput.java
│   ├── output/TransactionOutput.java
│   ├── ListTransactionsByCategoryUseCase.java
│   └── PersistTransactionUseCase.java
├── domain/                          # Regras de negócio puras e entidades
│   ├── Category.java
│   ├── Transaction.java
│   ├── TransactionId.java
│   └── TransactionRepository.java
└── infrastructure/                  # Adaptadores externos e HTTP
    ├── http/                        # Controllers REST e mapeadores
    │   ├── request/TransactionRequest.java
    │   ├── response/TransactionResponse.java
    │   └── TransactionController.java
    └── persistence/                 # Spring Data JPA e mapeamento ORM
        ├── entity/TransactionEntity.java
        └── repository/JpaTransactionRepository.java
```

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 21 / 25
- **Framework:** Spring Boot 3.3.4
- **IA Generativa:** Spring AI 2.0.0-M4 (`spring-ai-starter-model-openai`)
- **Persistência:** Spring Data JPA, Hibernate, MySQL 9
- **Conteinerização:** Docker & Docker Compose
- **Build Tool:** Gradle 8+
- **Testes & Asserções:** JUnit 5, AssertJ, Spring Boot Test

---

## ⚙️ Como Executar o Projeto

### Pré-requisitos
- JDK 21 ou superior instalado
- Docker e Docker Compose em execução
- Chave de API da OpenAI (`OPENAI_API_KEY`)

### 1. Clonar o projeto
```bash
git clone [https://github.com/diegofloriano/dio-budgeting-spring-ai.git](https://github.com/diegofloriano/dio-budgeting-spring-ai.git)
cd dio-budgeting-spring-ai
```

### 2. Configurar a Chave da OpenAI
No Windows (PowerShell):
```powershell
$env:OPENAI_API_KEY="sua-chave-openai"
```
No Linux/macOS:
```bash
export OPENAI_API_KEY="sua-chave-openai"
```

### 3. Iniciar o Banco de Dados com Docker
```bash
docker compose up -d
```

### 4. Executar a Aplicação
```bash
./gradlew bootRun
```
A API responderá em `http://localhost:8080`.

---

## 📍 Endpoints Principais

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/transactions/ai` | Recebe áudio multipart, transcreve via Whisper, aciona Use Case por Tool Calling e responde em MP3 |
| `POST` | `/transactions` | Persiste transação manual via payload JSON |
| `GET` | `/transactions/{category}` | Lista transações filtradas por categoria (`GROCERIES`, `PHARMA`, `AUTO`) |
| `GET` | `/api/chat-model?prompt=...` | Interação direta com o modelo de texto via LLM |
| `POST` | `/api/transcribe` | Transcrição isolada de arquivos de áudio |
| `POST` | `/api/sinthesize` | Síntese de voz avulsa a partir de texto |

---

## 🗺️️ Roadmap de Evolução da API (Visão de Produção)

Com base nas diretrizes finais de arquitetura do projeto, os próximos passos mapeados para a plataforma são:

- [ ] **Auditoria & Rastreabilidade Forense:** Adição de metadados na persistência (`timestamp`, canal de envio web/mobile e ID do usuário autenticado).
- [ ] **Armazenamento de Áudios (Object Storage):** Integração com AWS S3 / MinIO para arquivar as gravações originais vinculadas ao ID de cada transação.
- [ ] **Segurança Bancária:** Implementação de Spring Security com OAuth2/JWT para autenticação dos endpoints.
- [ ] **Integração de Microsserviços:** Conexão com serviços de cotação de moedas e validação antifraude utilizando Spring Cloud OpenFeign.
- [ ] **Arquitetura Desacoplada via MCP (Model Context Protocol):** Transformação das ferramentas em servidores MCP padronizados, permitindo que APIs em Node.js, Python ou PHP reutilizem os agentes de voz e Tool Calling da solução.

---

## 👨‍💻 Autor

Desenvolvido por **Diego Floriano Costa**  
Trilha Spring Boot & Spring AI — Digital Innovation One.
