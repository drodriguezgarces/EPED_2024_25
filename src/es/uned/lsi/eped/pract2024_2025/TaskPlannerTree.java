package es.uned.lsi.eped.pract2024_2025;


import es.uned.lsi.eped.DataStructures.BSTreeIF;
import es.uned.lsi.eped.DataStructures.IteratorIF;

public class TaskPlannerTree implements TaskPlannerIF{

	/* Declaración de atributos para almacenar la información del planificador de tareas */

	/* Estructura que almacena las tareas pasadas */
	protected BSTreeIF<TaskIF> pastTasks;
	/* La estructura que almacena las tareas futuras debe ser un BSTree */
	protected BSTreeIF<TaskIF> futureTasks;

	/* Añade una nueva tarea
	 * @param text: descripción de la tarea
	 * @param date: fecha en la que la tarea debe completarse
	 */
	public void add(String text,int date) {
		Task newTask = new Task(text, date); // Crear nueva tarea
	    futureTasks.add(newTask); // Añadir al árbol binario de búsqueda
	}

	/* Elimina una tarea
	 * @param date: fecha de la tarea que se debe eliminar
	 */
	public void delete(int date) {
		IteratorIF<TaskIF> it = futureTasks.iterator(BSTreeIF.IteratorModes.DIRECTORDER);
	    while (it.hasNext()) {
	        TaskIF task = it.getNext();
	        if (task.getDate() == date) {
	            futureTasks.remove(task); // Eliminar del árbol
	            break;
	        }
	    }
	}

	/* Reprograma una tarea
	 * @param origDate: fecha actual de la tarea
	 * @param newDate: nueva fecha de la tarea
	 */
	public void move(int origDate,int newDate) {
		Task taskToMove = null;
	    IteratorIF<TaskIF> it = futureTasks.iterator(BSTreeIF.IteratorModes.DIRECTORDER);

	    // Buscar y eliminar la tarea original
	    while (it.hasNext()) {
	        TaskIF task = it.getNext();
	        if (task.getDate() == origDate) {
	            taskToMove = (Task) task;
	            futureTasks.remove(task);
	            break;
	        }
	    }

	    // Si se encontró la tarea, actualizar la fecha y añadirla nuevamente
	    if (taskToMove != null) {
	        taskToMove.setDate(newDate);
	        futureTasks.add(taskToMove);
	    }
	}

	/* Ejecuta la próxima tarea:
	 * la mete en el histórico marcándola como completada
	 */
	public void execute() {
		if (!futureTasks.isEmpty()) {
	        IteratorIF<TaskIF> it = futureTasks.iterator(BSTreeIF.IteratorModes.DIRECTORDER);
	        TaskIF task = it.getNext(); // Obtener la tarea con menor fecha
	        futureTasks.remove(task); // Remover del árbol de tareas futuras
	        ((Task) task).setCompleted(); // Marcar como completada
	        pastTasks.add(task); // Añadir al árbol de tareas pasadas
	    }
	}

	/* Descarta la próxima tarea:
	 * la mete en el histórico marcándola como no completada
	 */
	public void discard() {
		if (!futureTasks.isEmpty()) {
	        IteratorIF<TaskIF> it = futureTasks.iterator(BSTreeIF.IteratorModes.DIRECTORDER);
	        TaskIF task = it.getNext(); // Obtener la tarea con menor fecha
	        futureTasks.remove(task); // Remover del árbol de tareas futuras
	        //((Task) task).setCompleted(); // Marcar como no completada
	        pastTasks.add(task); // Añadir al árbol de tareas pasadas
	    }
	}

	/* Devuelve un iterador de las tareas futuras */
	public IteratorIF<TaskIF> iteratorFuture() {
		return futureTasks.iterator(BSTreeIF.IteratorModes.DIRECTORDER);
		
	}

	/* Devuelve un iterador del histórico de tareas pasadas */
	public IteratorIF<TaskIF> iteratorPast() {
		return pastTasks.iterator(BSTreeIF.IteratorModes.DIRECTORDER);
		
	}
		
}
