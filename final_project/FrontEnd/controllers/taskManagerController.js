import { fetchTasks, createTask, updateTask, deleteTask } from '../services/taskApi.js';
import { renderTaskList } from '../views/taskManagerList.js';

export function initTaskController(container) {
  const form = container.querySelector('#task-form');
  const descriptionInput = form.querySelector('#task-description');

  // Função principal para obter e renderizar as tarefas
  const render = async () => {
    const tasks = await fetchTasks();
    renderTaskList(container, tasks, handleToggle, handleDelete, handleAddDetails);
  };

  // Marca ou desmarca como concluída
  const handleToggle = async (task) => {
    await updateTask(task.id, {
      ...task,
      completed: !task.completed
    });
    render();
  };

  // Apaga uma tarefa
  const handleDelete = async (task) => {
    await deleteTask(task.id);
    render();
  };

  // Atualiza detalhes e data
  const handleAddDetails = async (task, details, dueDate) => {
    await updateTask(task.id, {
      ...task,
      description: details || '',
      deadline: dueDate || ''
    });
    render();
  };

  // Submete nova tarefa
  form.onsubmit = async (e) => {
    e.preventDefault();
    const title = descriptionInput.value.trim();
    if (title === '') {
      descriptionInput.classList.add('border-red-500');
      return;
    }
    descriptionInput.classList.remove('border-red-500');
    await createTask(title);
    descriptionInput.value = '';
    render();
  };

  // Carrega as tarefas ao iniciar
  render();
  return { render };
}
