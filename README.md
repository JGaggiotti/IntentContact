# IntentContact
Prova di un Intent per selezionare un contatto
----
if(intent.resolveActivity(getPackageManager()) != null) { // da capire pk su alcune versioni di android funziona e altre no
}
----
if (cursor != null && cursor.moveToFirst()) { //forse si può usare solo cursor.moveToFirst();
}
