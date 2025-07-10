import { initTaskController } from './controllers/taskManagerController.js';
import { askJarvis } from './services/taskApi.js';

document.addEventListener('DOMContentLoaded', () => {
  const body = document.body;

  // Container principal da aplicação
  const container = document.createElement('div');
  container.className =
    'max-w-md mx-auto mt-10 bg-white/10 backdrop-blur p-6 rounded shadow-lg';
  body.appendChild(container);

  // Título da aplicação
  const title = document.createElement('h1');
  title.className = 'text-2xl font-bold mb-4 text-center text-white';
  title.textContent = 'Jarvis Task Manager';
  container.appendChild(title);

  // Formulário para adicionar nova tarefa
  const form = document.createElement('form');
  form.id = 'task-form';
  form.className = 'flex gap-2 mb-4';

  // Label invisível para acessibilidade
  const descriptionLabel = document.createElement('label');
  descriptionLabel.textContent = 'Descrição da tarefa';
  descriptionLabel.htmlFor = 'task-description';
  descriptionLabel.className = 'sr-only';
  form.appendChild(descriptionLabel);

  // Input de texto para nova tarefa
  const descriptionInput = document.createElement('input');
  descriptionInput.id = 'task-description';
  descriptionInput.className =
    'flex-1 border border-gray-600 p-2 rounded bg-gray-800 text-white placeholder-gray-400';
  descriptionInput.placeholder = 'Nova tarefa...';
  form.appendChild(descriptionInput);

  // Botão para adicionar tarefa
  const button = document.createElement('button');
  button.className = 'bg-blue-600 text-white px-4 py-2 rounded';
  button.textContent = 'Adicionar';
  form.appendChild(button);

  // Junta o formulário ao container
  container.appendChild(form);

  // 🔹 Inicializa o controlador de tarefas
  const taskManagerController = initTaskController(container);

  // 🔹 Botão flutuante para abrir o chat
  const chatButton = document.createElement('button');
  chatButton.className = `
    fixed bottom-6 right-6 w-14 h-14 rounded-full overflow-hidden
    shadow-lg z-50 hover:scale-105 transition-transform duration-200
    bg-transparent border-none p-0
  `;

  const chatIcon = document.createElement('img');
  chatIcon.src = '/resources/chatbot.png';
  chatIcon.className = 'w-full h-full object-cover';
  chatButton.appendChild(chatIcon);
  body.appendChild(chatButton);

  // 🔹 Caixa de chat (inicialmente escondida)
  const chatBox = document.createElement('div');
  chatBox.className = `
    fixed bottom-24 right-6 w-80 max-h-[400px] bg-white/10 backdrop-blur
    text-white rounded shadow-lg z-50 flex flex-col overflow-hidden
    border border-gray-500 hidden
  `;

  // Conteúdo HTML da caixa de chat
  chatBox.innerHTML = `
    <div class="flex justify-between items-center px-4 py-2 font-bold bg-white/10 backdrop-blur border-b border-gray-500">
      <span>Jarvis Chat</span>
      <button id="close-chat" class="text-white text-lg font-bold hover:text-red-400">✕</button>
    </div>
    <div id="chat-messages" class="flex-1 p-3 overflow-y-auto text-sm space-y-2 text-white"></div>
    <form id="chat-form" class="p-3 flex gap-2 border-t border-gray-500">
      <input type="text" id="chat-input" class="flex-1 border border-gray-600 p-2 rounded bg-gray-800 text-white placeholder-gray-400 text-sm" placeholder="Escreve uma mensagem..." />
      <button class="bg-blue-600 text-white px-4 rounded">Enviar</button>
    </form>
  `;
  body.appendChild(chatBox);

  // 🔹 Mostrar/esconder chat
  chatButton.onclick = () => {
    chatBox.classList.toggle('hidden');
  };

  // 🔹 Fechar chat ao clicar no "✕"
  document.getElementById('close-chat').onclick = () => {
    chatBox.classList.add('hidden');
  };

  // 🔹 Lógica de envio da mensagem via chat
  document.getElementById('chat-form').onsubmit = async (e) => {
    e.preventDefault();

    const input = document.getElementById('chat-input');
    const msg = input.value.trim();
    if (!msg) return;

    const chatMessages = document.getElementById('chat-messages');

    // Mostra mensagem do utilizador
    const userMsg = document.createElement('div');
    userMsg.className = 'text-right';
    userMsg.textContent = msg;
    chatMessages.appendChild(userMsg);

    input.value = '';

    // Mostra mensagem de espera do bot
    const botMsg = document.createElement('div');
    botMsg.className = 'text-left text-blue-400';
    botMsg.textContent = 'Jarvis está a pensar...';
    chatMessages.appendChild(botMsg);

    chatMessages.scrollTop = chatMessages.scrollHeight;

    try {
      // Envia pergunta ao Jarvis e recebe resposta
      const resposta = await askJarvis(msg);
      botMsg.textContent = resposta;

      // Atualiza lista de tarefas (caso o Jarvis tenha criado uma)
      taskManagerController.render();

      chatMessages.scrollTop = chatMessages.scrollHeight;
    } catch (error) {
      botMsg.textContent = 'Ocorreu um erro ao contactar o Jarvis.';
    }
  };
});
