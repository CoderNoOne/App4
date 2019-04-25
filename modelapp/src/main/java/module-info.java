module modelapp {
  requires exceptionsapp;
  exports model.car to mainapp, serviceapp, convertersapp, validatorsapp;
  exports model.statistics to mainapp, serviceapp;
  exports model.sorting to mainapp, serviceapp;
  opens model.car to gson;
}