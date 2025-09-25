# 💸 API Transactions — Sécurisée avec JWT

Cette API a été développée pour une application de **bancaire**.
Elle est basée sur **Spring Boot** et sécurisée via des **tokens JWT** afin de garantir une authentification robuste et sans état (*stateless*).

---

## 📋 Table des matières

- [🚀 Démarrage rapide](#-démarrage-rapide)
- [🔐 Authentification JWT](#-authentification-jwt)
- [📚 Documentation des endpoints](#-documentation-des-endpoints)
- [🏦 Gestion des comptes](#-gestion-des-comptes)
- [📋 Format des réponses](#-format-des-réponses)
- [🛡️ Sécurité](#️-sécurité)
- [⚙️ Configuration](#️-configuration)
- [🧪 Tests rapides](#-tests-rapides)
- [🚨 Gestion des erreurs](#-gestion-des-erreurs)
- [🔄 Workflow d'utilisation](#-workflow-dutilisation)
- [✅ Points clés](#-points-clés)

---

## 🚀 Démarrage rapide

### 1. Lancer l'application
```bash
mvn spring-boot:run
```

### 2. Accéder à l'interface Swagger
👉 **http://localhost:8080/swagger-ui.html**

### 3. Tester les endpoints
Utilisez Swagger ou Postman pour tester les endpoints.

---

## 🔐 Authentification JWT

### 1. Connexion

**Endpoint :** `POST /api/auth/login`
**Content-Type :** `application/json`

#### Exemple de requête :
```json
{
  "email": "john.doe@example.com",
  "password": "motdepasse123"
}
```

#### Réponse :
```json
{
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "type": "Bearer"
  },
  "isSuccess": true,
  "message": "Connexion réussie",
  "timestamp": 1703123456789,
  "status": "SUCCESS"
}
```

---

## 📚 Documentation des endpoints

Toute la documentation interactive des endpoints est disponible via **Swagger** :

👉 **http://localhost:8080/swagger-ui.html**

### Comment utiliser Swagger ?

1. **Ouvrir Swagger** dans le navigateur
2. **Cliquer sur Authorize** (🔒 en haut à droite)
3. **Entrer le token JWT** sous la forme :
   ```
   Bearer <votre_token>
   ```
4. **Tester directement** les endpoints (publics et protégés)

---

## 🏦 Gestion des comptes

### Caractéristiques des comptes
- **Création automatique** à l'inscription
- **Numéro unique** généré automatiquement
- **Solde initial** : 0 CFA
- **Devise** : CFA
- **Statut** : Actif

---

## 📋 Format des réponses

### ✅ Succès
```json
{
  "data": { ... },
  "isSuccess": true,
  "message": "Message de succès",
  "timestamp": 1703123456789,
  "status": "SUCCESS"
}
```

### ❌ Erreur
```json
{
  "data": null,
  "isSuccess": false,
  "message": "Message d'erreur",
  "timestamp": 1703123456789,
  "status": "ERROR"
}
```

---

## 🛡️ Sécurité

### Fonctionnalités de sécurité
- **JWT** : Tokens stateless, durée de vie configurable
- **BCrypt** : Chiffrement des mots de passe
- **Authentification obligatoire** pour tous les endpoints sauf ceux d'authentification

---

## ⚙️ Configuration

### Configuration JWT (application.properties)
```properties
# JWT
jwt.secret=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
jwt.expiration=86400000
```

#### Paramètres :
- **jwt.secret** : clé de signature des tokens
- **jwt.expiration** : durée de vie du token en millisecondes (24h)

---

## 🧪 Tests rapides (via cURL)

### 1. Connexion
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"password123"}'
```

### 2. Profil utilisateur (JWT requis)
```bash
curl -X GET http://localhost:8080/api/utilisateurs/me \
  -H "Authorization: Bearer <votre_token>"
```

---

## 🚨 Gestion des erreurs

| Code | Description |
|------|-------------|
| **400** | Bad Request → Données invalides |
| **401** | Unauthorized → Token manquant ou invalide |
| **403** | Forbidden → Accès refusé |
| **404** | Not Found → Ressource introuvable |

---

## 🔄 Workflow d'utilisation

1. **Inscription** → Création utilisateur + compte bancaire automatique
2. **Connexion** → Récupération du token JWT
3. **Authentification** → Utilisation du token dans les requêtes protégées
4. **Gestion du compte** → Consultation et mises à jour

---

## ✅ Points clés

- ✅ Chaque nouvel utilisateur dispose d'un compte bancaire automatiquement créé
- ✅ Tous les comptes commencent avec 0 CFA
- ✅ Les mots de passe sont hashés avec BCrypt
- ✅ Les tokens JWT expirent au bout de 24h
- ✅ Utilisez toujours l'en-tête : `Authorization: Bearer <token>`

---