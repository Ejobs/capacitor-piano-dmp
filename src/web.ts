import { WebPlugin } from '@capacitor/core';

import type { PianoDmpPlugin } from './definitions';

export class PianoDmpWeb extends WebPlugin implements PianoDmpPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
