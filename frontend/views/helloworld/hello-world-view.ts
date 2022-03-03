import '@vaadin/button';
import '@vaadin/notification';
import { Notification } from '@vaadin/notification';
import '@vaadin/text-field';
import { html } from 'lit';
import { customElement, state } from 'lit/decorators.js';
import { View } from '../../views/view';

@customElement('hello-world-view')
export class HelloWorldView extends View {

  @state()
  name = '';

  connectedCallback() {
    super.connectedCallback();
    this.classList.add('flex', 'p-m', 'gap-m', 'items-end');
  }

  render() {
    return html`
      <vaadin-text-field label="Your name" @value-changed=${this.nameChanged}></vaadin-text-field>
      <vaadin-button @click=${this.callText}>Call text REST endpoint</vaadin-button>
      <vaadin-button @click=${this.callPdf}>Call pdf REST endpoint</vaadin-button>
      <a router-ignore href="/pdf?name=${this.name}">A link to the PDF endpoint using the entered name</a>
    `;
  }

  nameChanged(e: CustomEvent) {
    this.name = e.detail.value;
  }

  callText() {
    window.location.href = `/text?name=${this.name}`;
  }
  callPdf() {
    window.location.href = `/pdf?name=${this.name}`;
  }
}
