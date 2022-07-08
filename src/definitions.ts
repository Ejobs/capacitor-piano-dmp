export interface PianoDmpPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
