$(document).ready(function () {

    if ($(window).width() <= 980) {

        var altura = $(window).height() - 150;

        $('#calendar').fullCalendar({
            defaultView: 'listMonth',
            firstDay: 1,
            editable: true,
            overlap: true,
            slotEventOverlap: true,
            eventLimit: true,
            eventStartEditable: true,
            eventDurationEditable: true,
            height: altura,
            selectable: true,
            selectHelper: true,
            nowIndicator: true,
            navLinks: true, // can click day/week names to navigate views
            editable: true,
                    select: function (start, end) {

                        swal({
                            title: "Nuevo evento",
                            type: "input",
                            showCancelButton: true,
                            closeOnConfirm: false,
                            animation: "slide-from-top",
                            inputPlaceholder: "Nombre del evento"
                        },
                                function (inputValue) {

                                    if (inputValue === false)
                                        return false;

                                    if (inputValue === "") {
                                        swal.showInputError("Escribe el nombre del evento");
                                        return false
                                    }
                                    var title = inputValue;

                                    var eventData;
                                    if (title) {
                                        eventData = {
                                            title: title,
                                            start: start,
                                            end: end
                                        };
                                        $('#calendar').fullCalendar('renderEvent', eventData, true); // stick? = true
                                    }
                                    $('#calendar').fullCalendar('unselect');
                                    console.log(eventData.start._d);
                                    console.log(eventData.end._d);
                                    console.log(title);
                                    swal("Nuevo evento", inputValue, "success");
                                });

                    },
            droppable: true,
            drop: function (date) {
                alert("Dropped on " + dateTime.format());
            },
            buttonText: {
                today: 'Hoy',
                month: 'Mes',
                week: 'Semana',
                day: 'Día',
                listWeek: 'Agenda'
            },
            header: {
                left: 'today, month,agendaWeek,agendaDay',
                center: 'prev, title, next',
                right: 'listWeek, prevYear,nextYear'
            },
            monthNames:
                    ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio',
                        'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
            monthNamesShort:
                    ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun',
                        'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
            dayNames:
                    ['Domingo', 'Lunes', 'Martes', 'Miércoles',
                        'Jueves', 'Viernes', 'Sábado'],
            dayNamesShort:
                    ['Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sáb'],
            eventLimit: true, // for all non-agenda views
                    views: {
                        agenda: {
                            eventLimit: 6 // adjust to 6 only for agendaWeek/agendaDay
                        }
                    },
            buttonIcons:
                    {
                        prev: 'angle-left',
                        next: 'angle-right',
                        prevYear: 'angle-double-left',
                        nextYear: 'angle-double-right'
                    },
            eventDrop: function (event, delta, revertFunc) {

                var letras = event.start.format().toString();
                var cant = letras.length;
                console.log(event.title + " ha sido movido a: " + letras.substring(0, 10) + " a las " + letras.substring(11, cant));

            },
            eventResize: function (event, delta, revertFunc) {

                var letras = event.end.format().toString();
                var cant = letras.length;
                console.log(event.title + " La fecha final cambió a: " + letras.substring(0, 10));
                console.log(event.title + " La hora final cambió a: " + letras.substring(11, cant));

            },
            eventClick: function (calEvent, jsEvent, view) {

                swal({
                    title: calEvent.title,
                    type: "input",
                    inputType: 'input',
                    showCancelButton: true,
                    closeOnConfirm: false,
                    confirmButtonText: 'Guardar',
                    cancelButtonText: 'Eliminar',
                    confirmButtonColor: '#2895cc',
                    animation: "slide-from-top",
                    inputPlaceholder: "Nuevo nombre del evento"
                },
                        function (inputValue) {

                            if (inputValue === false)
                                return false;

                            if (inputValue === "") {
                                swal.showInputError("Escribe el nombre del evento");
                                return false
                            }
                            var title = inputValue;

                            if (title) {
                                calEvent.title = title;
                                $('#calendar').fullCalendar('updateEvent', calEvent);
                            }

                            swal("Listo", "El evento cambió a: " + inputValue, "success");
                        });
            },
            events: [
                {
                    title: 'Servicio técnico',
                    start: '2016-11-01',
                    backgroundColor: '#2895cc',
                    className: 'Diagnstico computadora Lenovo'
                },
                {
                    title: 'Reparación de computador',
                    start: '2016-12-13',
                    end: '2016-12-13',
                    backgroundColor: '#2895cc',
                    className: 'Cra 5c no 48 f 24'
                },
                {
                    id: 99,
                    title: 'Instalación de cámaras',
                    start: '2016-11-11T16:00:00',
                    backgroundColor: '#2895cc',
                    className: 'Diag 54 f no 24 Instalació de cámaras'
                },
                {
                    id: 999,
                    title: 'Mantenimiento',
                    start: '2016-11-16T16:00:00',
                    end: '2016-11-16T12:00:00',
                    backgroundColor: '#2895cc',
                    className: 'Mantenimiento portàtil Cliente: Álvaro Gonzalez'
                },
                {
                    id: 10,
                    title: 'Viaje de trabajo',
                    start: '2016-11-07',
                    end: '2016-12-12',
                    backgroundColor: '#1d9d74',
                    className: 'Viaje de negocios a Barranquilla'
                },
                {
                    title: 'Informes jefe',
                    start: '2016-11-12T10:30:00',
                    end: '2016-11-12T12:30:00',
                    backgroundColor: '#1d9d74',
                    className: 'Entrega de informes empresa'
                },
                {
                    title: 'Almuerzo',
                    start: '2016-11-12T12:00:00',
                    backgroundColor: '#d00',
                    className: 'Mi almuerzo'
                },
                {
                    title: 'Cumpleaños',
                    start: '2016-12-26T14:30:00',
                    backgroundColor: '#d00',
                    className: 'Cumpleaños familiar'
                },
                {
                    title: 'Cotización',
                    start: '2016-11-12T17:30:00',
                    backgroundColor: '#2895cc',
                    className: 'Solicitud de cotizaciòn'
                },
                {
                    title: 'Cena',
                    start: '2016-11-12T20:00:00',
                    backgroundColor: '#d00',
                    className: 'Cenar'
                },
                {
                    title: 'Fiesta',
                    start: '2016-11-13T07:00:00',
                    backgroundColor: '#d00',
                    className: 'Matrimonio'
                },
            ]
        });
    }

    $('#eliminarEvento').click(function () {

        swal("Here's a message!");

    });

    $('#calendar').fullCalendar({
        firstDay: 1,
        editable: true,
        overlap: true,
        slotEventOverlap: true,
        eventLimit: true,
        eventStartEditable: true,
        eventDurationEditable: true,
        height: 570,
        selectable: true,
        selectHelper: true,
        nowIndicator: true,
        navLinks: true, // can click day/week names to navigate views
        editable: true,
                select: function (start, end) {

                    swal({
                        title: "Nuevo evento",
                        type: "input",
                        showCancelButton: true,
                        closeOnConfirm: false,
                        animation: "slide-from-top",
                        inputPlaceholder: "Nombre del evento",
                        confirmButtonText: 'Agregar',
                        cancelButtonText: 'Cancelar',
                        confirmButtonColor: '#2895cc',
                        animation: "slide-from-top",
                    },
                            function (inputValue) {

                                if (inputValue === false)
                                    return false;

                                if (inputValue === "") {
                                    swal.showInputError("Escribe el nombre del evento");
                                    return false
                                }
                                var title = inputValue;

                                var eventData;
                                if (title) {
                                    eventData = {
                                        title: title,
                                        start: start,
                                        end: end
                                    };
                                    $('#calendar').fullCalendar('renderEvent', eventData, true); // stick? = true
                                }
                                $('#calendar').fullCalendar('unselect');
                                console.log(eventData.start._d);
                                console.log(eventData.end._d);
                                console.log(title);
                                swal("Nuevo evento", inputValue, "success");
                            });

                },
        droppable: true,
        drop: function (date) {
            alert("Dropped on " + dateTime.format());
        },
        buttonText: {
            today: 'Hoy',
            month: 'Mes',
            week: 'Semana',
            day: 'Día',
            listWeek: 'Agenda'
        },
        header: {
            left: 'today, month,agendaWeek,agendaDay',
            center: 'title',
            right: 'listWeek, prev,next, prevYear,nextYear',
        },
        monthNames:
                ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio',
                    'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
        monthNamesShort:
                ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun',
                    'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
        dayNames:
                ['Domingo', 'Lunes', 'Martes', 'Miércoles',
                    'Jueves', 'Viernes', 'Sábado'],
        dayNamesShort:
                ['Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sáb'],
        eventLimit: true, // for all non-agenda views
                views: {
                    agenda: {
                        eventLimit: 6 // adjust to 6 only for agendaWeek/agendaDay
                    }
                },
        buttonIcons:
                {
                    prev: 'angle-left',
                    next: 'angle-right',
                    prevYear: 'angle-double-left',
                    nextYear: 'angle-double-right'
                },
        eventDrop: function (event, delta, revertFunc) {

            var letras = event.start.format().toString();
            var cant = letras.length;
            console.log(event.title + " ha sido movido a: " + letras.substring(0, 10) + " a las " + letras.substring(11, cant));

        },
        eventResize: function (event, delta, revertFunc) {

            var letras = event.end.format().toString();
            var cant = letras.length;
            console.log(event.title + " La fecha final cambió a: " + letras.substring(0, 10));
            console.log(event.title + " La hora final cambió a: " + letras.substring(11, cant));

        },
        eventClick: function (calEvent, jsEvent, view) {

            swal({
                title: calEvent.title,
                type: "input",
                inputType: 'input',
                showCancelButton: true,
                closeOnConfirm: false,
                confirmButtonText: 'Guardar',
                cancelButtonText: 'Eliminar',
                confirmButtonColor: '#2895cc',
                animation: "slide-from-top",
                inputPlaceholder: "Nuevo nombre del evento"
            },
                    function (inputValue) {

                        if (inputValue === false)
                            return false;

                        if (inputValue === "") {
                            swal.showInputError("Escribe el nombre del evento");
                            return false
                        }
                        var title = inputValue;

                        if (title) {
                            calEvent.title = title;
                            $('#calendar').fullCalendar('updateEvent', calEvent);
                        }

                        swal("Listo", "El evento cambió a: " + inputValue, "success");
                    });
        },
        events: [
            {
                title: 'Servicio técnico',
                start: '2016-11-01',
                backgroundColor: '#2895cc',
                className: 'Diagnstico computadora Lenovo'
            },
            {
                title: 'Reparación de computador',
                start: '2016-12-13',
                end: '2016-12-13',
                backgroundColor: '#2895cc',
                className: 'Cra 5c no 48 f 24'
            },
            {
                id: 99,
                title: 'Instalación de cámaras',
                start: '2016-11-11T16:00:00',
                backgroundColor: '#2895cc',
                className: 'Diag 54 f no 24 Instalació de cámaras'
            },
            {
                id: 999,
                title: 'Mantenimiento',
                start: '2016-11-16T16:00:00',
                end: '2016-11-16T12:00:00',
                backgroundColor: '#2895cc',
                className: 'Mantenimiento portàtil Cliente: Álvaro Gonzalez'
            },
            {
                id: 10,
                title: 'Viaje de trabajo',
                start: '2016-11-07',
                end: '2016-12-12',
                backgroundColor: '#1d9d74',
                className: 'Viaje de negocios a Barranquilla'
            },
            {
                title: 'Informes jefe',
                start: '2016-11-12T10:30:00',
                end: '2016-11-12T12:30:00',
                backgroundColor: '#1d9d74',
                className: 'Entrega de informes empresa'
            },
            {
                title: 'Almuerzo',
                start: '2016-11-12T12:00:00',
                backgroundColor: '#d00',
                className: 'Mi almuerzo'
            },
            {
                title: 'Cumpleaños',
                start: '2016-12-26T14:30:00',
                backgroundColor: '#d00',
                className: 'Cumpleaños familiar'
            },
            {
                title: 'Cotización',
                start: '2016-11-12T17:30:00',
                backgroundColor: '#2895cc',
                className: 'Solicitud de cotizaciòn'
            },
            {
                title: 'Cena',
                start: '2016-11-12T20:00:00',
                backgroundColor: '#d00',
                className: 'Cenar'
            },
            {
                title: 'Fiesta',
                start: '2016-11-13T07:00:00',
                backgroundColor: '#d00',
                className: 'Matrimonio'
            },
        ]
    });

});
