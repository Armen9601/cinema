// (function (a) {
//     jQuery(document).ready(function (b) {
//         b(document).on("submit", "#contact_form_submit", function (c) {
//             c.preventDefault();
//             var g = b("#name").val();
//             var d = b("#email").val();
//             var f = b("#message").val();
//             var h = b("#subject").val();
//             if (!g) {
//                 b("#name").removeClass("error");
//                 b("#name").addClass("error").attr("placeholder", "Please Enter Name")
//             } else {
//                 b("#name").removeClass("error")
//             }
//             if (!d) {
//                 b("#email").removeClass("error");
//                 b("#email").addClass("error").attr("placeholder", "Please Enter Email")
//             } else {
//                 b("#email").removeClass("error")
//             }
//             if (!f) {
//                 b("#message").removeClass("error");
//                 b("#message").addClass("error").attr("placeholder", "Please Enter Your Message")
//             } else {
//                 b("#message").removeClass("error")
//             }
//             if (!h) {
//                 b("#subject").removeClass("error");
//                 b("#subject").addClass("error").attr("placeholder", "Please Enter Your Subject")
//             } else {
//                 b("#subject").removeClass("error")
//             }
//             if (g && d && f && h) {
//                 b.ajax({
//                     type: "POST",
//                     url: "contact.php",
//                     data: {name: g, email: d, message: f, subject: h,},
//                     success: function (e) {
//                         b("#contact_form_submit").children(".email-success").remove();
//                         b("#contact_form_submit").prepend('<span class="alert alert-success email-success">' + e + "</span>");
//                         b("#name").val("");
//                         b("#email").val("");
//                         b("#message").val("");
//                         b("#subject").val("");
//                         b(".email-success").fadeOut(1000)
//                     },
//                     error: function (e) {
//                     }
//                 })
//             } else {
//                 b("#contact_form_submit").children(".email-success").remove();
//                 b("#contact_form_submit").prepend('<span class="alert alert-danger email-success">Somenthing went wrong</span>');
//                 b(".email-success").fadeOut(1000)
//             }
//         })
//     })
// }(jQuery));