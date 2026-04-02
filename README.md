# Spring AI RAG (Google Gemini, Java 25)

Spring Boot 3.5 + Spring AI ile Google Gemini API üzerinden **RAG** (Retrieval Augmented Generation) örneği.  
Tüm sohbet istekleri `QuestionAnswerAdvisor` ile vektör deposundan bağlam çekerek modele gider.

## Gereksinimler

- **JDK 25** (Gradle toolchain ile otomatik eşleştirilebilir)
- Google Gemini API key (`GEMINI_API_KEY`)

## Ortam değişkeni

```bash
export GEMINI_API_KEY="your_api_key"
```

## Çalıştırma

```bash
./gradlew bootRun
```

Uygulama: `http://localhost:8080`

## API

**POST** `/api/chat`  
JSON gövde: `{ "message": "..." }`  
Yanıt: `{ "reply": "..." }`

Örnek:

```bash
curl -s -X POST http://localhost:8080/api/chat \
  -H "Content-Type: application/json" \
  -d '{"message":"Acme RAG Demo destek kanalı nedir?"}'
```

## Bilgi tabanı

Örnek metin [`src/main/resources/kb/knowledge-base.txt`](src/main/resources/kb/knowledge-base.txt) dosyasındadır. Uygulama açılışında bu metin embedding ile indekslenir. İçeriği güncelledikten sonra uygulamayı yeniden başlatın.

## Yapılandırma

| Özellik | Konum |
|--------|--------|
| Gemini API key, chat/embedding modelleri | `spring.ai.google.genai.*` |
| RAG `topK` ve benzerlik eşiği | `app.rag.top-k`, `app.rag.similarity-threshold` |

## Derleme

```bash
./gradlew build
```
