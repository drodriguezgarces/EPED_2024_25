package es.uned.lsi.eped.pract2024_2025;

import java.util.ArrayList;

import es.uned.lsi.eped.DataStructures.IteratorIF;
import es.uned.lsi.eped.DataStructures.Sequence;
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

	public TaskPlannerSequence() {
	    this.futureTasks = new SequenceIF<>(); // Inicializa futureTasks
	    this.pastTasks = new SequenceIF<TaskIF>();  // Inicializa pastTasks
	}
	
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
		while (index < futureTasks.size() && futureTasks.get(index).getDate() < date) {
			index++;
		}
		futureTasks.add(index, newTask); // Inserta en la posición correcta para mantener el orden
	}

	/*
	 * Elimina una tarea
	 * 
	 * @param date: fecha de la tarea que se debe eliminar
	 */
	public void delete(int date) {
		futureTasks.removeIf(task -> task.getDate() == date); // Elimina la tarea con la fecha dada
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
		for (Task task : futureTasks) {
			if (task.getDate() == origDate) {
				taskToMove = task;
				break;
			}
		}
		if (taskToMove != null) {
			futureTasks.remove(taskToMove); // Elimina la tarea de la lista
			this.add(taskToMove.getText(), newDate); // La vuelve a añadir con la nueva fecha
		}
	}

	/*
	 * Ejecuta la próxima tarea: la mete en el histórico marcándola como completada
	 */
	public void execute() {
		if (!futureTasks.isEmpty()) {
			Task task = futureTasks.remove(0); // Saca la tarea más cercana
			task.setCompleted(); // La marca como completada
			pastTasks.add(task); // La añade al histórico
		}
	}

	/*
	 * Descarta la próxima tarea: la mete en el histórico marcándola como no
	 * completada
	 */
	public void discard() {
		if (!futureTasks.isEmpty()) {
	        Task task = futureTasks.remove(0); // Saca la tarea más cercana
	        pastTasks.add(task); // La añade al histórico sin marcarla como completada
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
