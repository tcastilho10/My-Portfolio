const API_URL = 'http://localhost:8081/api/tasks';
const AI_API_URL = 'http://localhost:8081/api/ai/ask';

/**
 * Vai buscar todas as tarefas do backend
 */
export async function fetchTasks() {
  try {
    const response = await fetch(API_URL);
    if (!response.ok) throw new Error('Erro ao buscar tarefas');
    return await response.json();
  } catch (err) {
    console.error('fetchTasks:', err);
    return [];
  }
}

/**
 * Cria uma nova tarefa
 * @param {string} title 
 */
export async function createTask(title) {
  try {
    const response = await fetch(API_URL, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        title: title,
        description: '',
        deadline: '',
        completed: false
      })
    });
    if (!response.ok) throw new Error('Erro ao criar tarefa');
    return await response.json();
  } catch (err) {
    console.error('createTask:', err);
    return null;
  }
}

/**
 * Atualiza uma tarefa (por ID)
 * @param {number} id 
 * @param {object} updates 
 */
export async function updateTask(id, updates) {
  try {
    const response = await fetch(`${API_URL}/${id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(updates)
    });
    if (!response.ok) throw new Error('Erro ao atualizar tarefa');
    return await response.json();
  } catch (err) {
    console.error('updateTask:', err);
    return null;
  }
}

/**
 * Apaga uma tarefa (por ID)
 * @param {number} id 
 */
export async function deleteTask(id) {
  try {
    const response = await fetch(`${API_URL}/${id}`, {
      method: 'DELETE'
    });
    if (!response.ok) throw new Error('Erro ao apagar tarefa');
    return true;
  } catch (err) {
    console.error('deleteTask:', err);
    return false;
  }
}

/**
 * Envia pergunta para o Jarvis AI e obtém resposta
 * @param {string} input 
 */
export async function askJarvis(input) {
  try {
    const response = await fetch(AI_API_URL, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(input), 
    });
    if (!response.ok) throw new Error('Erro ao comunicar com Jarvis AI');
    return await response.text();
  } catch (err) {
    console.error('askJarvis:', err);
    return 'Erro na comunicação com o Jarvis AI';
  }
}
