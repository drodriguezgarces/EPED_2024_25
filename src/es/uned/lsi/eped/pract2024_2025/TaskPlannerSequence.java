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
		Task newTask = new Task(text, date);
		int index = 0;

		IteratorIF<TaskIF> it = futureTasks.iterator();
		while (it.hasNext()) {
			TaskIF task = it.getNext();
			if (task.getDate() > date) {
				break;
			}
			index++;
		}
		//futureTasks.setValue(index, newTask);
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
				//futureTasks.remove(index);
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

		// Encuentra y elimina la tarea con la fecha original
		while (it.hasNext()) {
			TaskIF task = it.getNext();
			if (task.getDate() == origDate) {
				taskToMove = (Task) task;
				//futureTasks.remove(index);
				break;
			}
			index++;
		}

		// Si se encontró la tarea, se actualiza la fecha y se vuelve a añadir
		if (taskToMove != null) {
			//set(taskToMove.getDate(), newDate);
		}
	}

	/*
	 * Ejecuta la próxima tarea: la mete en el histórico marcándola como completada
	 */
	public void execute() {
		if (!futureTasks.isEmpty()) {
			TaskIF task = (TaskIF) futureTasks.iterator(); // Obtiene la primera tarea
			//futureTasks.remove(0); // La elimina de las tareas futuras
			task.setCompleted(); // Marca la tarea como completada
			//pastTasks.add(task); // La añade al histórico
		}
	}

	/*
	 * Descarta la próxima tarea: la mete en el histórico marcándola como no
	 * completada
	 */
	public void discard() {
		if (!futureTasks.isEmpty()) {
			TaskIF task = futureTasks.getFirst(); // Obtiene la primera tarea
	        futureTasks.clear(); // La elimina de las tareas futuras
	        task.setCompleted(); // Marca la tarea como no completada
	        //pastTasks.add(task); // La añade al histórico
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
