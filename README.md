# AppPlaces
Esta app tiene tiene como funcionabilidad registrarse e iniciar sesión, en al cual podra ver lugares que otras personas compartieron.
Ademas de poder compartir un lugar llenando los campos (descripcion, fotos 2 maximo).

Para el desarrollo se utilizo:
Firebase para el registro y autenticacion de los usuerios, ademas de almacenarlo en su base datos cloud firestore a los usuarios, lugares y
para las fotos se guardaron en cloud store.

El patron MVP, para tener estructurado el proyecto separando el interfaz del usuario con la logica del aplicación, trabajando de manera 
separada para cuando se realice una actualización en un capa no afecte a las demas.
  -Vista: son las interfaces que el usuario interactua.
  -Modelo: se encarga de la logica de la aplicación, ademas de ser utilizado como una persistencia para obtener información de la bd.
  -Presentador: se encarga en la comunicación entre la vista y el modelo. Es como un puente entre las dos capas ya que el es independiente.

Ademas se utilizo Location de android para obtener la ubicación del usuario y AlarmManager para programar una tarea en que cada 30min
guarde la ubicacion.


