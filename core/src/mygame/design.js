$('#cdd').on('h:focusout', function ()
    {
        if (HSelect.getInputOf('#cdd').val() == -1)
            return;
        DateWidget.manageLegalBirthDate($(this), '#birthDateHContainer');
        decideToAddLoading();
        countryListener();
    }
);
