module convertersapp {
  requires modelapp;
  requires exceptionsapp;
  requires java.sql;
  requires gson;
  requires validatorsapp;
  exports converters.json to serviceapp;
  exports converters.others to serviceapp;
}