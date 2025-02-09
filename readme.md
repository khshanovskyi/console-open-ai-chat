# Implement your custom console chat with AI assistant

## Pre Steps:
1. 🔑 Add to the project Open-AI API key https://platform.openai.com/settings/organization/api-keys (It is not Free, you
   need to pay 1-10$ for subscription)
2. Use this URL to connect to OpenAI API https://api.openai.com/v1/chat/completions

## You need to create CONSOLE application that will:
1. Apply user message from console
2. Send user message with all conversation history to OpenAI API
3. Stream response from AI to console
4. Finish application via command `exit` in console

## Steps:
PAY ATTENTION PROJECT WON'T COMPILE UNTIL YOU IMPLEMENT `Role` ENUM AND PASS THE `RoleTest`
1. Create enum with roles (`enum Role`) with roles `system`, `user`, `assistant`
2. Create DTO for message, that will consist of `role` and `content`
3. Create DTO for conversation, that will have of `id` and `messages` list
4. Create `task.OpenAIClient` that applies history, go to Open AI API, stream response to console and return
   AI `message`
5. Create `task.ChatApp` with `main` method that will work with console
6. **Don't forget to add Open AI `API_KEY` in `Constant` class**

<details> 
<summary>Examples of Open AI API requests</summary>

**Only required fields in request body:**
```json
{
  "model": "gpt-4o-mini",
  "messages": [
    {
      "role": "system",
      "content": "You are a helpful assistant."
    },
    {
      "role": "user",
      "content": "What is the capital of France?"
    }
  ]
}
```
**Example with optional fields in request body**
```json
{
  "model": "gpt-4o-mini",
  "messages": [
    {
      "role": "system",
      "content": "You are a helpful assistant."
    },
    {
      "role": "user",
      "content": "What is the capital of France?"
    }
  ],
  "temperature": 0.7,
  "max_tokens": 100,
  "top_p": 1,
  "frequency_penalty": 0,
  "presence_penalty": 0,
  "stream": true
}
```
Full request:
```
POST https://api.openai.com/v1/chat/completions
Authorization: Bearer YOUR_API_KEY
Content-Type: application/json

{
  "model": "gpt-4",
  "messages": [
    {
      "role": "system",
      "content": "You are a helpful assistant."
    },
    {
      "role": "user",
      "content": "What is the capital of France?"
    }
  ],
  "stream": true
}

```

</details> 

<details> 
<summary>Examples of Open AI API responses from streaming</summary>

<b>Pay attention that it starts from 'data: ' (it has 6 chars and then content)</b>

```
data: {
  "id": "chatcmpl-7nZC2pX5L4hq7Uj8FjldjGmc",
  "object": "chat.completion.chunk",
  "created": 1677652280,
  "model": "gpt-4",
  "choices": [
    {
      "delta": {
        "role": "assistant"
      },
      "index": 0,
      "finish_reason": null
    }
  ]
}
```

```
data: {
  "id": "chatcmpl-7nZC2pX5L4hq7Uj8FjldjGmc",
  "object": "chat.completion.chunk",
  "created": 1677652281,
  "model": "gpt-4",
  "choices": [
    {
      "delta": {
        "content": "Hello"
      },
      "index": 0,
      "finish_reason": null
    }
  ]
}
```

```
data: {
  "id": "chatcmpl-7nZC2pX5L4hq7Uj8FjldjGmc",
  "object": "chat.completion.chunk",
  "created": 1677652282,
  "model": "gpt-4",
  "choices": [
    {
      "delta": {
        "content": "!"
      },
      "index": 0,
      "finish_reason": null
    }
  ]
}
```

When streaming is finished it returns `[DONE]`
```
data: [DONE]
```
</details> 

## TEST scenarios:
<details> 
<summary>Calculator:</summary>

- Prompt:
  ```
  You are a calculator. Your role is to perform mathematical computations and output the result as a number. Do NOT include any words, explanations, or units in your responses. Only provide the numeric result of the calculation.
  ```
- Scenario:
    ```
  Hi, what can u do?
  ```
  ~ '0'. Or 'Hi, I'm calculator and I can...'
    ```
  3*8
  ```
  ~ 24
    ```
  /2
  ```
  ~ 12
    ```
  -3
  ```
  ~ 9
    ```
  ^2
  ```
  ~ 81
</details> 

<details> 
<summary>Ongoing Technical Troubleshooting:</summary>

- Prompt:
  ```
  You are a Python expert and troubleshooting specialist. Your role is to assist in diagnosing and resolving technical issues related to Python programming. Provide clear, concise, and step-by-step solutions, ensuring the user understands the reasoning behind each step. When applicable, include example code, best practices, or alternative approaches. If the issue involves debugging, highlight the root cause and suggest efficient ways to fix or optimize the code. Always prioritize clarity, accuracy, and actionable advice.
  ```
- Scenario:
  ```
  Hi, what can u do?
  ```
  ~ Hi! I specialize in assisting with Python programming issues. Here's how I can help...
    ```
  I'm getting an error while running my Python script.
  ```
  ~ ... What’s the error message?
    ```
  'ModuleNotFoundError: No module named requests'.
  ```
  ~ This means the 'requests' library isn’t installed. You can install it by running `pip install requests`...
    ```
  I tried that, but now it says 'Permission denied'.
  ```
  ~ It seems you might not have the necessary permissions. Try using `sudo pip install` requests or run the command in a virtual environment.
    ```
  I set up the virtual environment, and now it works. But another error came up: 'ConnectionError'.
  ```
  ~ The 'ConnectionError' suggests an issue with your internet or the URL you’re trying to access...
</details> 

## Main Criteria for Application Functionality:
1. Streaming in Console:
    > Ensure that the application streams output continuously in the console, reflecting real-time interactions or updates.

2. Conversation History Support:
    > The application should support a history of conversations, allowing LLM to see previous interactions.
   
