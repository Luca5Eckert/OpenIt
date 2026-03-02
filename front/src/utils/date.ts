/**
 * Parse an ISO date string, handling missing timezone info.
 * The backend returns dates in format "yyyy-MM-dd'T'HH:mm:ss" without timezone.
 * We assume these are UTC dates.
 */
export function parseISODate(isoString: string | null | undefined): Date | null {
  if (!isoString) return null;

  // If the string already has timezone info (ends with Z, +, or -), parse directly
  if (/[Z+-]\d{0,2}:?\d{0,2}$/.test(isoString) || isoString.endsWith('Z')) {
    return new Date(isoString);
  }

  // Otherwise, assume UTC and append Z
  return new Date(isoString + 'Z');
}

/**
 * Format a date for display in Brazilian Portuguese.
 */
export function formatDate(isoString: string | null | undefined): string {
  const date = parseISODate(isoString);
  if (!date || isNaN(date.getTime())) return '--';

  return date.toLocaleString('pt-BR', {
    timeZone: 'America/Sao_Paulo',
    day: '2-digit',
    month: 'short',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
  });
}

/**
 * Calculate time difference between two ISO date strings.
 */
export function calculateTimeSpent(
  entryIso: string | null | undefined,
  exitIso: string | null | undefined
): string {
  const entry = parseISODate(entryIso);
  const exit = parseISODate(exitIso);

  if (!entry || !exit) return '--';

  const diffMs = exit.getTime() - entry.getTime();

  if (diffMs < 0) return 'Erro no cálculo';

  const diffHrs = Math.floor(diffMs / (1000 * 60 * 60));
  const diffMins = Math.floor((diffMs % (1000 * 60 * 60)) / (1000 * 60));

  if (diffHrs === 0) {
    return `${diffMins} min`;
  }
  return `${diffHrs}h e ${diffMins} min`;
}

/**
 * Format currency in Brazilian Real.
 */
export function formatCurrency(value: number): string {
  return new Intl.NumberFormat('pt-BR', {
    style: 'currency',
    currency: 'BRL',
  }).format(value);
}
