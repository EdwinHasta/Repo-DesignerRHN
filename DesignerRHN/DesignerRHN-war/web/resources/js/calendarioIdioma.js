PrimeFaces.locales['es'] = {
    closeText: 'Cerrar',
    prevText: 'Anterior',
    nextText: 'Siguiente',
    monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
    monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
    dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
    dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab'],
    dayNamesMin: ['D', 'L', 'M', 'Mi', 'J', 'V', 'S'],
    weekHeader: 'Semana',
    firstDay: 1,
    isRTL: false,
    showMonthAfterYear: false,
    yearSuffix: '',
    timeOnlyTitle: 'Sólo hora',
    timeText: 'Tiempo',
    hourText: 'Hora',
    minuteText: 'Minuto',
    secondText: 'Segundo',
    currentText: 'Fecha actual',
    ampm: false,
    month: 'Mes',
    week: 'Semana',
    day: 'Día',
    allDayText: 'Todo el día'
};

PrimeFaces.widget.Dialog.prototype.applyFocus = function() {
    var firstInput = this.jq.find(':not(:submit):not(:button):input:visible:enabled:first');
    if (!firstInput.hasClass('hasDatepicker')) {
        firstInput.focus();
    }
};

function comprobantesScroll() {
    datosComprobantes.scrollOffset = 3;
}

function cortesProcesosScroll() {
    datosCortesProcesos.scrollOffset = 3;
}
/*SCROLL DINAMICOS TABLAS CON COLUMNA FIJA*/
function iniciarScrollEmpleado() {
    var leftDiv = document.getElementById('inferiorIzquierda');
    var rightDiv = document.getElementById('inferiorDerecha');
    var topDiv = document.getElementById('superiorDerecha');
    
    rightDiv.onscroll = function() {
        leftDiv.scrollTop = rightDiv.scrollTop;
        topDiv.scrollLeft = rightDiv.scrollLeft;
    };
    leftDiv.onscroll = function() {
        rightDiv.scrollTop = leftDiv.scrollTop;
    };
}

function iniciarScrollEmpleador() {
    var leftDiv = document.getElementById('inferiorIzquierdaEM');
    var rightDiv = document.getElementById('inferiorDerechaEM');
    var topDiv = document.getElementById('superiorDerechaEM');

    rightDiv.onscroll = function() {
        leftDiv.scrollTop = rightDiv.scrollTop;
        topDiv.scrollLeft = rightDiv.scrollLeft;
    };
    leftDiv.onscroll = function() {
        rightDiv.scrollTop = leftDiv.scrollTop;
    };
}

