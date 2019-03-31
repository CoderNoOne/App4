module modelapp {
  exports model.car to mainapp,serviceapp;
  exports model.statistics to mainapp,serviceapp;
  exports model.sorting to mainapp, serviceapp;
  exports model.converters.others to serviceapp;
}