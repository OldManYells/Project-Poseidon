#!/usr/bin/env bash
set -euo pipefail

ROOT="src/main/java/com/legacyminecraft/poseidon"
PATTERN='org\.bukkit\.|net\.minecraft\.server\.'

if [[ ! -d "$ROOT" ]]; then
  echo "Dependency-direction check skipped: $ROOT not found."
  exit 0
fi

if command -v rg >/dev/null 2>&1; then
  MATCHES="$(rg -n "$PATTERN" "$ROOT" || true)"
else
  MATCHES="$(grep -R -n -E "$PATTERN" "$ROOT" || true)"
fi

if [[ -n "$MATCHES" ]]; then
  echo "Dependency-direction violation detected."
  echo "Forbidden references in $ROOT:"
  echo "$MATCHES"
  exit 1
fi

echo "Dependency-direction check passed: no Bukkit/NMS references found in Poseidon sources."
