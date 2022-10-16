import React from "react";

export default class MainMenu extends React.Component { 

  render(){
    return(
      <>
        <picture onclick="location.href='https://github.com/Binder-Creations/RogueBasic'">
          <source th:srcset='@{/images/logo.png}' media='(min-width: 961px)'/>
          <img class="bc-logo" th:src="@{/images/logo-small.png}" alt="logo"/>
        </picture>
        <div class="spacer-l"></div>
        <picture>
            <source th:srcset='@{/images/title.png}' media='(min-width: 961px)'/>
            <img class ="title" th:src='@{/images/title-small.png}' alt="title"/>
          </picture>
          <div class="spacer-xl"></div>
        <button class="btn-l font-l" th:if="${has_characters}" type="button" onclick="location.href='/character_select'">Character Select</button>
        <div class="spacer-xs" th:if="${has_characters}"></div>
        <button class="btn-l font-l" type="button" onclick="location.href='/new_character'">Create New Character</button>
        <div class="spacer-xs"></div>
        <button class="btn-l font-l" type="button" onclick="location.href='/spend_souls'">Spend Souls</button>
        <div class="spacer-xs"></div>
        <button class="btn-l font-l" type="button" onclick="location.href='/login?logout=true'">Logout</button>
        <div class="spacer-s"></div>
        <div class="spacer-m" th:if="${temp}"></div>
        <button class="btn-l font-xxs" th:if="${temp}" type="button" onclick="location.href='/register'">
          <p class="font-gold">This is a Temporary Account</p>
          <p class="font-gold">Make it Permanent Here</p>
        </button>
      </>
    );
  }
}