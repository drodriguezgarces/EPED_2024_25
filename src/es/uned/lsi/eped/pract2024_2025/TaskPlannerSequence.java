package es.uned.lsi.eped.pract2024_2025;

import es.uned.lsi.eped.DataStructures.IteratorIF;
import es.uned.lsi.eped.DataStructures.SequenceIF;

public class TaskPlannerSequence implements TaskPlannerIF {

	/*
	 * Declaración de atributos para almacenar la información del planificador de
	 * tareas
	 */

	/* Estructura que almacena las tareas pasadas */
	protected SequenceIF<TaskIF> pastTasks;
	/* La estructura que almacena las tareas futuras debe ser una secuencia */
	protected SequenceIF<TaskIF> futureTasks;

	/*
	 * Añade una nueva tarea
	 * 
	 * @param text: descripción de la tarea
	 * 
	 * @param date: fecha en la que la tarea debe completarse
	 */
	public void add(String text, int date) {
		Task newTask = new Task(text, date); // Crear nueva tarea
	    int index = 0;

	    // Encontrar la posición adecuada para insertar la tarea
	    IteratorIF<TaskIF> it = futureTasks.iterator();
	    while (it.hasNext()) {
	        TaskIF task = it.getNext();
	        if (task.getDate() > date) {
	            break;
	        }
	        index++;
	    }
	    // Insertar la tarea en la posición encontrada
	    futureTasks.insert(index, newTask);
	}

	/*
	 * Elimina una tarea
	 * 
	 * @param date: fecha de la tarea que se debe eliminar
	 */
	public void delete(int date) {
		IteratorIF<TaskIF> it = futureTasks.iterator();
	    int index = 0;

	    while (it.hasNext()) {
	        TaskIF task = it.getNext();
	        if (task.getDate() == date) {
	            futureTasks.remove(index);
	            break;
	        }
	        index++;
	    }
	}

	/*
	 * Reprograma una tarea
	 * 
	 * @param origDate: fecha actual de la tarea
	 * 
	 * @param newDate: nueva fecha de la tarea
	 */
	public void move(int origDate, int newDate) {
		Task taskToMove = null;
	    IteratorIF<TaskIF> it = futureTasks.iterator();
	    int index = 0;

	    // Encontrar y eliminar la tarea con la fecha original
	    while (it.hasNext()) {
	        TaskIF task = it.getNext();
	        if (task.getDate() == origDate) {
	            taskToMove = (Task) task;
	            futureTasks.remove(index);
	            break;
	        }
	        index++;
	    }

	    // Si se encontró la tarea, actualizar la fecha y añadirla nuevamente
	    if (taskToMove != null) {
	        taskToMove.setDate(newDate);
	        add(taskToMove.getText(), newDate);
	    }
	}

	/*
	 * Ejecuta la próxima tarea: la mete en el histórico marcándola como completada
	 */
	public void execute() {
		if (!futureTasks.isEmpty()) {
	        TaskIF task = futureTasks.get(0); // Obtener la primera tarea
	        futureTasks.remove(0); // Remover de tareas futuras
	        ((Task) task).setCompleted(); // Marcar como completada
	        pastTasks.add(task); // Añadir al histórico
	    }
	}

	/*
	 * Descarta la próxima tarea: la mete en el histórico marcándola como no
	 * completada
	 */
	public void discard() {
		if (!futureTasks.isEmpty()) {
	        TaskIF task = futureTasks.get(0); // Obtener la primera tarea
	        futureTasks.remove(0); // Remover de tareas futuras
	        //((Task) task).setCompleted(false); // Marcar como no completada
	        pastTasks.add(task); // Añadir al histórico
	    }
	}

	/* Devuelve un iterador de las tareas futuras */
	public IteratorIF<TaskIF> iteratorFuture() {
		return futureTasks.iterator();
	}

	/* Devuelve un iterador del histórico de tareas pasadas */
	public IteratorIF<TaskIF> iteratorPast() {
		return pastTasks.iterator();
	}

}
