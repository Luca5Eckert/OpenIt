import js from '@eslint/js'
import globals from 'globals'
import reactHooks from 'eslint-plugin-react-hooks'
import reactRefresh from 'eslint-plugin-react-refresh'
import tseslint from 'typescript-eslint'
import { defineConfig, globalIgnores } from 'eslint/config'

export default defineConfig([
  globalIgnores(['dist']),
  {
    files: ['**/*.{ts,tsx}'],
    extends: [
      js.configs.recommended,
      tseslint.configs.recommended,
      reactHooks.configs.flat.recommended,
      reactRefresh.configs.vite,
    ],
    languageOptions: {
      ecmaVersion: 2020,
      globals: globals.browser,
    },
    rules: {
      // Allow setState in effects for subscription patterns (SSE, WebSocket, etc.)
      // This is a valid pattern when subscribing to external event sources like EventSource.
      // The setState calls are necessary to reflect external state changes in the UI.
      // See: https://react.dev/learn/synchronizing-with-effects#subscribing-to-events
      'react-hooks/set-state-in-effect': 'off',
    },
  },
])
