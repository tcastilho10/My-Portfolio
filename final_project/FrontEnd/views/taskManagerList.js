// Função que desenha a lista de tarefas no container

export function renderTaskList(container, tasks, onToggle, onDelete, onAddDetails) {
  // Encontra ou cria o <ul> que conterá as tarefas
  let ul = container.querySelector('ul');
  if (!ul) {
    ul = document.createElement('ul');
    ul.className = 'space-y-2';
    container.appendChild(ul);
  } else {
    ul.innerHTML = ''; // Limpa a lista antes de redesenhar
  }

  // Mostra mensagem se não houver tarefas
  if (tasks.length === 0) {
    const emptyMsg = document.createElement('p');
    emptyMsg.className = 'text-gray-500 text-center';
    emptyMsg.textContent = 'Nenhuma tarefa adicionada.';
    ul.appendChild(emptyMsg);
    return;
  }

  // Ordena tarefas: incompletas primeiro, depois por data de deadline
  tasks = tasks.sort((a, b) => {
    if (a.completed !== b.completed) return a.completed ? 1 : -1;
    return new Date(a.deadline || 0) - new Date(b.deadline || 0);
  });

  // Criação visual de cada tarefa
  tasks.forEach(task => {
    const li = document.createElement('li');
    li.className = 'p-2 border rounded';

    const taskContainer = document.createElement('div');
    taskContainer.className = 'flex justify-between items-start';

    if (task.completed) {
      taskContainer.classList.add('line-through', 'text-gray-400');
    }

    // Secção esquerda da tarefa (texto e detalhes)
    const leftSection = document.createElement('div');
    leftSection.className = 'flex flex-col';

    // Título da tarefa com ID
    const descriptionSpan = document.createElement('span');
    descriptionSpan.textContent = `#${task.id} - ${task.title || task.description}`;
    descriptionSpan.className = 'text-lg font-semibold';

    // Botão de alternância de detalhes
    const toggleDetailsBtn = document.createElement('button');
    toggleDetailsBtn.textContent = (task.description || task.deadline) ? 'Mostrar Detalhes' : 'Adicionar Detalhes';
    toggleDetailsBtn.className = 'text-blue-600 text-sm mt-1';

    // Secção de detalhes da tarefa (se existentes)
    const detailsContainer = document.createElement('div');
    detailsContainer.className = 'hidden mt-2 text-sm text-gray-300';

    if (task.description) {
      const descText = document.createElement('p');
      descText.textContent = `Detalhes: ${task.description}`;
      detailsContainer.appendChild(descText);
    }

    if (task.deadline) {
      // Validação e formatação de data
      const d = new Date(task.deadline);
      const formattedDate = isNaN(d.getTime()) ? '' : d.toLocaleDateString('pt-PT');
      if (formattedDate) {
        const deadlineText = document.createElement('p');
        deadlineText.textContent = `Data de Conclusão: ${formattedDate}`;
        detailsContainer.appendChild(deadlineText);
      }
    }

    // Formulário de edição de detalhes
    const detailsForm = document.createElement('form');
    detailsForm.className = 'hidden flex flex-col gap-2 mt-2';

    const detailsLabel = document.createElement('label');
    detailsLabel.className = 'flex flex-col';
    detailsLabel.textContent = 'Descrição';

    const detailsInput = document.createElement('textarea');
    detailsInput.className = 'border border-gray-600 p-2 rounded mt-1 bg-gray-800 text-white placeholder-gray-400';
    detailsInput.placeholder = 'Descrição detalhada...';
    detailsInput.rows = 2;
    detailsInput.value = task.description || '';

    detailsLabel.appendChild(detailsInput);

    const dueDateLabel = document.createElement('label');
    dueDateLabel.className = 'flex flex-col';
    dueDateLabel.textContent = 'Data de Conclusão';

    const dueDateInput = document.createElement('input');
    dueDateInput.type = 'date';
    dueDateInput.className = 'border border-gray-600 p-2 rounded bg-gray-800 text-white';

    if (task.deadline) {
      const d = new Date(task.deadline);
      dueDateInput.value = isNaN(d.getTime()) ? '' : d.toISOString().split('T')[0];
    } else {
      dueDateInput.value = '';
    }

    dueDateLabel.appendChild(dueDateInput);

    detailsForm.appendChild(detailsLabel);
    detailsForm.appendChild(dueDateLabel);

    // Botões do formulário
    const buttonsContainer = document.createElement('div');
    buttonsContainer.className = 'flex gap-2';

    const saveButton = document.createElement('button');
    saveButton.textContent = 'Guardar';
    saveButton.className = 'flex-1 bg-blue-600 text-white px-2 py-1 rounded';

    const cancelButton = document.createElement('button');
    cancelButton.textContent = 'Cancelar';
    cancelButton.type = 'button';
    cancelButton.className = 'flex-1 bg-gray-300 text-black px-2 py-1 rounded';

    // Cancela edição de detalhes
    cancelButton.onclick = () => {
      detailsForm.classList.add('hidden');
      actions.classList.remove('hidden');

      if (task.description || task.deadline) {
        detailsContainer.classList.remove('hidden');
        toggleDetailsBtn.textContent = 'Mostrar Detalhes';
        toggleDetailsBtn.classList.remove('hidden');
      } else {
        detailsContainer.classList.add('hidden');
        toggleDetailsBtn.textContent = 'Adicionar Detalhes';
        toggleDetailsBtn.classList.remove('hidden');
      }
    };

    buttonsContainer.appendChild(saveButton);
    buttonsContainer.appendChild(cancelButton);
    detailsForm.appendChild(buttonsContainer);

    // Submete alterações dos detalhes
    detailsForm.onsubmit = (e) => {
      e.preventDefault();
      const newDetails = detailsInput.value.trim();
      const newDeadline = dueDateInput.value || null;
      onAddDetails(task, newDetails, newDeadline);
    };

    // Mostra ou oculta o formulário de detalhes
    toggleDetailsBtn.onclick = () => {
      const isFormHidden = detailsForm.classList.contains('hidden');
      if (isFormHidden) {
        detailsForm.classList.remove('hidden');
        detailsContainer.classList.add('hidden');
        actions.classList.add('hidden');
        toggleDetailsBtn.classList.add('hidden');
      } else {
        detailsForm.classList.add('hidden');
        actions.classList.remove('hidden');
        toggleDetailsBtn.classList.remove('hidden');

        if (task.description || task.deadline) {
          detailsContainer.classList.remove('hidden');
        }
      }
    };

    // 🧩 Construção final da secção esquerda
    leftSection.appendChild(descriptionSpan);
    leftSection.appendChild(toggleDetailsBtn);
    leftSection.appendChild(detailsContainer);
    leftSection.appendChild(detailsForm);

    // 🎮 Secção de ações (Concluir / Apagar)
    const actions = document.createElement('div');
    actions.className = 'flex items-center space-x-2';

    const toggleBtn = document.createElement('button');
    toggleBtn.className = 'text-green-600';
    toggleBtn.setAttribute('aria-label', 'Marcar como concluída');
    toggleBtn.textContent = task.completed ? 'Desfazer' : 'Concluir';
    toggleBtn.onclick = () => onToggle(task);

    const deleteBtn = document.createElement('button');
    deleteBtn.className = 'text-red-600';
    deleteBtn.setAttribute('aria-label', 'Excluir tarefa');
    deleteBtn.textContent = 'Apagar';
    deleteBtn.onclick = () => onDelete(task);

    actions.appendChild(toggleBtn);
    actions.appendChild(deleteBtn);

    // 🧱 Composição da tarefa
    taskContainer.appendChild(leftSection);
    taskContainer.appendChild(actions);

    li.appendChild(taskContainer);
    ul.appendChild(li);
  });
}
