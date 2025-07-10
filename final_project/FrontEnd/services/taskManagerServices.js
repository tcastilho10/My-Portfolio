// Recupera as tarefas guardadas no localStorage ou inicializa com array vazio
let tasks = JSON.parse(localStorage.getItem('jarvis_tasks_v1')) || [];

// Função que guarda as tarefas atualizadas no localStorage
function saveTasks() {
  localStorage.setItem('jarvis_tasks_v1', JSON.stringify(tasks));
}

// Retorna uma cópia do array de tarefas para evitar alterações diretas
export function getTasks() {
  return [...tasks];
}

// Adiciona uma nova tarefa com descrição e valores padrão para os outros campos
export function addTask(description) {
  const task = {
    description,
    details: '',   // Detalhes extra da tarefa, inicialmente vazios
    dueDate: null, // Data de entrega, inicialmente nula
    completed: false // Estado da tarefa, inicialmente não concluída
  };
  tasks.push(task);  // Adiciona a tarefa ao array
  saveTasks();       // Guarda as alterações no armazenamento local
  return task;       // Retorna a tarefa criada
}

// Atualiza detalhes e data de entrega da tarefa no índice indicado
export function updateTaskDetails(index, details, dueDate) {
  tasks[index].details = details || '';   // Atualiza detalhes, ou deixa vazio se undefined
  tasks[index].dueDate = dueDate || null; // Atualiza data, ou deixa nula se undefined
  saveTasks();                            // Guarda as alterações
}

// Alterna o estado concluído/não concluído da tarefa no índice indicado
export function toggleTask(index) {
  tasks[index].completed = !tasks[index].completed; // Inverte o valor booleano
  saveTasks();                                      // Guarda as alterações
}

// Remove a tarefa no índice indicado do array
export function deleteTask(index) {
  tasks.splice(index, 1);  // Remove um elemento do array
  saveTasks();             // Guarda as alterações
}
