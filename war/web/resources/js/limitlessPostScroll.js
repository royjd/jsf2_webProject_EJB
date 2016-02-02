/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    var load = false; // aucun chargement de commentaire n'est en cours

    /* la fonction offset permet de récupérer la valeur X et Y d'un élément
     dans une page. Ici on récupère la position du dernier div qui 
     a pour classe : ".commentaire" */
    var offset = $('.post:last').offset();
    var last_id = $('.post:last').attr('id');
    $(window).scroll(function () { // On surveille l'évènement scroll

        /* Si l'élément offset est en bas de scroll, si aucun chargement 
         n'est en cours, si le nombre de commentaire affiché est supérieur 
         à 5 et si tout les commentaires ne sont pas affichés, alors on 
         lance la fonction. */
        if ((offset.top - $(window).height() <= $(window).scrollTop())
                && load == false) {
            //alert("LODING");
            // la valeur passe à vrai, on va charger
            load = true;

            //On récupère l'id du dernier commentaire affiché



            //On affiche un loader
            $('.loadmore').show();
            var url = $('#loadmore').attr('path');

            //On lance la fonction ajax
            $.ajax({
                url: url + last_id + '.htm',
                type: 'get',
                //Succès de la requête
                success: function (data) {
                    //alert(data);
                    //On masque le loader
                    $('.loadmore').fadeOut(500);
                    /* On affiche le résultat après
                     le dernier commentaire */
                    $('#' + last_id).after(data);
                    /* On actualise la valeur offset
                     du dernier commentaire */
                    offset = $('.post:last').offset();
                    //On remet la valeur à faux car c'est fini
                    load = false;
                    if (last_id == $('.post:last').attr('id')) {
                        load = true;
                        $('.nomoreloadmore').show();
                    } else {
                        last_id = $('.post:last').attr('id');
                    }

                },
                error: function (xhr, ajaxOptions, thrownError) {
                   // alert(xhr.status);
                    //alert(thrownError);
                }
            });
        }


    });
});