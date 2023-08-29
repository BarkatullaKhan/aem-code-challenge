
/**
 * Begin Code - Barkatulla Khan
 */

document.addEventListener("DOMContentLoaded", (event) => {
  document
    .getElementsByName("submitBtn")[0]
    .setAttribute("onclick", "handleSubmit(event)");
});

async function handleSubmit(e) {
  e.preventDefault();
  var formElem = document.getElementsByName("new_form")[0];
  var messageContainer = document.createElement("h2");
  messageContainer.setAttribute("id", "msgContainer");
  var userData = JSON.stringify({
    firstName: new_form.firstName.value,
    lastName: new_form.lastName.value,
    age: Number(new_form.age.value),
    country: new_form.country.value,
  });
  // console.log(userData);
  var response = await fetch("/bin/saveUserDetails", {
    method: "POST",
    body: userData,
    headers: {
      "Content-type": "application/json; charset=UTF-8",
    },
  });
  var output = await response.json();
  if (output.errorMessage != undefined) {
    messageContainer.innerText = output.errorMessage;
    messageContainer.style.backgroundColor = "red";
    if (!document.contains(document.getElementById("msgContainer"))) {
      formElem.prepend(messageContainer);
    } else {
        document.getElementById("msgContainer").style.backgroundColor = "red";
      document.getElementById("msgContainer").innerText = output.errorMessage;
    }
  } else if (output.successMessage != undefined) {
    messageContainer.innerText = output.successMessage;
    messageContainer.style.backgroundColor = "green";
    if (!document.contains(document.getElementById("msgContainer"))) {
      formElem.prepend(messageContainer);
    } else {
      document.getElementById("msgContainer").style.backgroundColor = "green";
      document.getElementById("msgContainer").innerText = output.successMessage;
    }
  }
}

/**
 * END Code
 */