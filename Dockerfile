FROM php:8.3-apache

RUN a2enmod rewrite

RUN apt-get update && apt-get install -y \
    git unzip libpq-dev \
    && rm -rf /var/lib/apt/lists/*

RUN docker-php-ext-install pdo pdo_pgsql

COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

WORKDIR /var/www/html

COPY . .

RUN composer install --no-dev --prefer-dist --optimize-autoloader --no-interaction

# Créer les dossiers nécessaires
RUN mkdir -p var/cache var/log var/sessions
RUN chmod -R 777 var/

ENV APP_ENV=prod
ENV APP_DEBUG=0

# Configuration Apache pour Render
ENV APACHE_DOCUMENT_ROOT=/var/www/html/public
RUN sed -ri -e 's!/var/www/html!${APACHE_DOCUMENT_ROOT}!g' /etc/apache2/sites-available/*.conf
RUN sed -ri -e 's!/var/www/!${APACHE_DOCUMENT_ROOT}/!g' /etc/apache2/apache2.conf /etc/apache2/conf-available/*.conf

# Configuration du port pour Render
RUN echo 'Listen ${PORT:-10000}' > /etc/apache2/ports.conf
RUN echo '<VirtualHost *:${PORT:-10000}>' > /etc/apache2/sites-available/000-default.conf
RUN echo '    DocumentRoot ${APACHE_DOCUMENT_ROOT}' >> /etc/apache2/sites-available/000-default.conf
RUN echo '    <Directory ${APACHE_DOCUMENT_ROOT}>' >> /etc/apache2/sites-available/000-default.conf
RUN echo '        AllowOverride All' >> /etc/apache2/sites-available/000-default.conf
RUN echo '        Require all granted' >> /etc/apache2/sites-available/000-default.conf
RUN echo '    </Directory>' >> /etc/apache2/sites-available/000-default.conf
RUN echo '</VirtualHost>' >> /etc/apache2/sites-available/000-default.conf

RUN chown -R www-data:www-data /var/www/html
RUN chmod -R 755 /var/www/html

# Script de démarrage
RUN echo '#!/bin/bash' > /start.sh
RUN echo 'export PORT=${PORT:-10000}' >> /start.sh
RUN echo 'sed -i "s/\${PORT}/$PORT/g" /etc/apache2/ports.conf' >> /start.sh
RUN echo 'sed -i "s/\${PORT}/$PORT/g" /etc/apache2/sites-available/000-default.conf' >> /start.sh
RUN echo 'apache2-foreground' >> /start.sh
RUN chmod +x /start.sh

EXPOSE $PORT

CMD ["/start.sh"]
