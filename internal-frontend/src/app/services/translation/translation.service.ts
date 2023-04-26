import {Injectable} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {Observable} from 'rxjs';
import {MatSnackBar} from '@angular/material/snack-bar';
import { of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TranslationService {

  constructor(
    private snackBar: MatSnackBar,
    private translateService: TranslateService
  ) { }

  public loadDefaultLanguage(): void {
    this.translateService.use('de');
  }

  public showSnackbarOnError(): void {
    this.snackBar.open('Interner Fehler', 'OK');
  }

  public showSnackbar(message: string): void {
    this.getTranslation(message).subscribe((translation: string) => {
      this.snackBar.open(translation, '', {
        duration: 2000,
      });
    });
  }

  public getTranslation(key: string): Observable<string> {
    if (key === null || key?.length === 0) {
      return of('');
    }

    return this.translateService.get(key);
  }
}
