$(document).ready(function(){
  $(".active").click(function(){
    $.post(window.location,
    {
      purchase: $(this).attr('id'),
      cost: $(this).attr('value')
    },
      function(){
          window.location.reload();
     });
  });
});