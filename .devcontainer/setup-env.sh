#!/bin/bash

# Configuration files (templates)
readonly ENV_FILE_TEMPLATE=".devcontainer/.env.template"
readonly REALM_FILE_TEMPLATE="docker/keycloak/momoino-realm.json.template"

# Generates a secure random key in base64 format
# Returns: Base64 encoded random key or error code 1
function generate_secure_key() {
  if ! dd if=/dev/urandom bs=32 count=1 2>/dev/null | base64 | tr '+/' '-_' | tr -d '\n'; then
    echo "Error: Failed to generate secure key" >&2
    return 1
  fi
}

# Updates environment variables in a file
# Args:
#   $1 - Target file path (template)
#   $2 - Variables to substitute (comma-separated)
# Returns: 0 on success, 1 on failure
function update_environment_file() {
  local template_file="$1"
  local variables="$2"
  local output_file="${template_file%.template}"

  # Check if the output file already exists
  if [ -f "$output_file" ]; then
    echo "Skipping $output_file: The template file appears to be already templated. If you want to re-template, please remove $output_file and try again."
    return 0
  fi

  echo "Updating environment variables in: $output_file"

  # Substitute variables directly in the output file
  if ! envsubst "$variables" < "$template_file" > "$output_file"; then
    echo "Error: Failed to update variables in $output_file" >&2
    return 1
  fi

  # Validate output
  if [ ! -s "$output_file" ]; then
    echo "Error: Generated file is empty" >&2
    rm -f "$output_file"
    return 1
  fi

  # Set restrictive file permissions
  if ! chmod 600 "$output_file"; then
    echo "Error: Failed to set secure file permissions" >&2
    return 1
  fi

  echo "Successfully updated $output_file"
  return 0
}

# Generates all required security keys
# Args:
#   $1 - Variable to store client secret
#   $2 - Variable to store default password
#   $3 - Variable to store cookie secret
# Returns: 0 on success, 1 on failure
function generate_security_keys() {
  echo "Generating security keys..."

  local client_secret
  client_secret=$(generate_secure_key) || return 1
  export CLIENT_SECRET="$client_secret"

  local default_password
  default_password=$(generate_secure_key) || return 1
  export DEFAULT_PASSWORD="$default_password"

  echo "Security keys generated successfully"

  return 0
}

# Main execution flow
function main() {
  echo "=== Starting Environment Setup ==="

  # Generate security keys
  if ! generate_security_keys; then
    echo "Error: Failed to generate security keys" >&2
    exit 1
  fi

  # Update environment configuration
  if ! update_environment_file "$ENV_FILE_TEMPLATE" "\$CLIENT_SECRET,\$DEFAULT_PASSWORD"; then
    exit 1
  fi

  # Update Keycloak realm configuration
  if ! update_environment_file "$REALM_FILE_TEMPLATE" "\$CLIENT_SECRET"; then
    exit 1
  fi

  # Clean up backup files
  [ -f "${ENV_FILE_TEMPLATE%.template}.bak" ] && rm -f "${ENV_FILE_TEMPLATE%.template}.bak"
  [ -f "${REALM_FILE_TEMPLATE%.template}.bak" ] && rm -f "${REALM_FILE_TEMPLATE%.template}.bak"

  echo "=== Environment Setup Complete ==="
}

main
